package ru.btpit.nmedia.entyties

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
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
            if(it.contentText.trim() == text && it.contentPath == image){
                return
            }
            edited.value = it.copy(contentText = text, contentPath = image, author = post.author)
        }
    }

    fun edit(post: Post){
        edited.value = post
    }
}