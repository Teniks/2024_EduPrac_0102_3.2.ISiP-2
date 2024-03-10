package ru.btpit.nmedia.entyties

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.btpit.nmedia.interfaces.PostDAO

class AppDb private constructor(db: SQLiteDatabase){
    val postDao: PostDAO = PostDaoImpl(db)

    companion object{
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb{
            return instance ?: synchronized(this){
                instance ?: AppDb(
                    buildDataBase(context, arrayOf(PostDaoImpl.DDL))
                ).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs
        ).writableDatabase
    }
}