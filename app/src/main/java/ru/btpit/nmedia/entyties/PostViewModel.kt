package ru.btpit.nmedia.entyties

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.btpit.nmedia.interfaces.PostRepository

private val empty = Post(
    id = 0,
    author = "",
    contentText = "",
    published = "",
    quantityLikes = 0,
    quantityComments = 0,
    quantityReposts = 0,
    quantityViews = 0
)

class PostViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun comment(id: Long) = repository.comment(id)
    fun repost(id: Long) = repository.repost(id)
    fun view(id: Long) = repository.view(id)
    fun removeBuId(id: Long) = repository.removeById(id)

    fun save(){
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun changeContent(post: Post){
        edited.value?.let {
            val text = post.contentText.trim()
            val image = post.contentPath
            val url = findLink(post.contentText.trim())
            if(it.contentText.trim() == text && it.contentPath == image){
                return
            }
            var newPost = post.copy(contentText = text, contentPath = image, author = post.author)
            if(url != null){
                newPost = newPost.copy(urlVideo = url)
            }
            edited.value = newPost
        }
    }

    fun edit(post: Post){
        edited.value = post
    }

    private fun findLink(text: String): String?{
        val regex = Regex("""https\S+www\.youtube\.com\S+watch\?v=\S+""", setOf(RegexOption.IGNORE_CASE))
        val result = regex.find(text)
        return result?.value
    }
}