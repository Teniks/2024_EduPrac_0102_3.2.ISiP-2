package ru.btpit.nmedia.entyties

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.btpit.nmedia.interfaces.PostDAO

class PostDaoImpl(private val db: SQLiteDatabase) : PostDAO{
    companion object{

        val DDL: String = "CREATE TABLE \"Posts\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\t\"author\"\tTEXT NOT NULL,\n" +
                "\t\"contentText\"\tTEXT NOT NULL,\n" +
                "\t\"published\"\tTEXT NOT NULL,\n" +
                "\t\"quantityLikes\"\tINTEGER DEFAULT 0,\n" +
                "\t\"quantityComments\"\tINTEGER DEFAULT 0,\n" +
                "\t\"quantityReposts\"\tINTEGER DEFAULT 0,\n" +
                "\t\"quantityViews\"\tINTEGER DEFAULT 0,\n" +
                "\t\"contentPath\"\tINT DEFAULT NULL,\n" +
                "\t\"urlVideo\"\tTEXT DEFAULT NULL,\n" +
                "\t\"likedByMe\"\tINTEGER DEFAULT 0\n" +
                ")"
    }
    object PostColumns {
        const val TABLE = "Posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT_TEXT = "contentText"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_QUANTITY_LIKES = "quantityLikes"
        const val COLUMN_QUANTITY_COMMENTS = "quantityComments"
        const val COLUMN_QUANTITY_REPOSTS = "quantityReposts"
        const val COLUMN_QUANTITY_VIEWS = "quantityViews"
        const val COLUMN_CONTENT_PATH = "contentPath"
        const val COLUMN_URL_VIDEO = "urlVideo"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        val COLUMN_ALL = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT_TEXT,
            COLUMN_PUBLISHED,
            COLUMN_QUANTITY_LIKES,
            COLUMN_QUANTITY_COMMENTS,
            COLUMN_QUANTITY_REPOSTS,
            COLUMN_QUANTITY_VIEWS,
            COLUMN_CONTENT_PATH,
            COLUMN_URL_VIDEO,
            COLUMN_LIKED_BY_ME
        )
    }
    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.COLUMN_ALL,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()){
                posts.add(map(it))
            }
        }
        return posts
    }
    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            if(post.id != 0L){
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_AUTHOR, post.author)
            put(PostColumns.COLUMN_CONTENT_TEXT, post.contentText)
            put(PostColumns.COLUMN_PUBLISHED, post.published)
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.COLUMN_ALL,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use {
            it.moveToNext()
            return map(it)
        }
    }
    override fun likedById(id: Long) {
        db.execSQL(
            """
                UPDATE Posts SET
                quantityLikes = quantityLikes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id = ?;
            """.trimIndent(), arrayOf(id)
        )
    }
    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }
    override fun commentById(id: Long) {
        db.execSQL(
            """
                UPDATE Posts SET
                quantityComments = quantityComments + 1
                WHERE id = ?;
            """.trimIndent(), arrayOf(id)
        )
    }
    override fun repostById(id: Long) {db.execSQL(
        """
                UPDATE Posts SET
                quantityReposts = quantityReposts + 1
                WHERE id = ?;
            """.trimIndent(), arrayOf(id)
    )}
    override fun viewById(id: Long) {db.execSQL(
        """
                UPDATE Posts SET
                quantityViews = quantityViews + 1
                WHERE id = ?;
            """.trimIndent(), arrayOf(id)
    )}
    private fun map(cursor: Cursor): Post {
        with(cursor){
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                contentText = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT_TEXT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                quantityLikes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_QUANTITY_LIKES)),
                quantityComments = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_QUANTITY_COMMENTS)),
                quantityReposts = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_QUANTITY_REPOSTS)),
                quantityViews = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_QUANTITY_VIEWS)),
                contentPath = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT_PATH)),
                urlVideo = getString(getColumnIndexOrThrow(PostColumns.COLUMN_URL_VIDEO)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0
            )
        }
    }
}