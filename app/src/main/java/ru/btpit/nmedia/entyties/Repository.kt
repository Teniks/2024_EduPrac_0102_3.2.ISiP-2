package ru.btpit.nmedia.entyties

import android.content.Context
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.btpit.nmedia.interfaces.PostDAO
import ru.btpit.nmedia.interfaces.PostRepository
import java.time.LocalDateTime


class PostRepositorySQLiteImpl(private val dao: PostDAO): PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts.toList())
    private var lastId: Long = posts.size.toLong()
    private fun nextId(): Long{
        lastId = posts.size.toLong() + 1
        return lastId
    }
    init {
        posts = dao.getAll()
        data.value = posts
    }
    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post){
        val id = post.id
        val saved = dao.save(post)

        posts = if(id == 0L){
            listOf(saved) + posts
        }else{
            posts.map{
                if(it.id != id) it else saved
            }
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, quantityLikes =
            if(it.likedByMe) it.quantityLikes-1 else it.quantityLikes+1)
        }
        data.value = posts
    }

    override fun repost(id: Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(quantityReposts = it.quantityReposts+1)
        }
        data.value = posts
    }

    override fun comment(id: Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(quantityComments = it.quantityComments+1)
        }
        data.value = posts
    }

    override fun view(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(quantityViews = it.quantityViews+1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        lastId--
        data.value = posts
    }

    override fun edit(post: Post, newText: String) {
        val time = LocalDateTime.now().dayOfMonth.toString() + " " + LocalDateTime.now().month + " " + LocalDateTime.now().hour + ":" + LocalDateTime.now().minute
        posts = listOf(
            post.copy(
                contentText = newText,
                published = time,
            )) + posts
        data.value = posts
        return
    }
}
