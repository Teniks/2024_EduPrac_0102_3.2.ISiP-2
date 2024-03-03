package ru.btpit.nmedia.entyties

import android.content.Context
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.btpit.nmedia.interfaces.PostRepository
import java.time.LocalDateTime


class PostRepositoryFileImpl(private val context: Context): PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts.toList())

    init {
        try {
            val file = context.filesDir.resolve(filename)
            if(file.exists()) {
                context.openFileInput(filename).bufferedReader().use {
                    posts = gson.fromJson(it, type)
                    data.value = posts
                }
            }else{
                sync()
            }
        }catch(_: Exception){

        }
    }
    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post){
        if(post.id == 0L){
            val time = LocalDateTime.now().dayOfMonth.toString() + " " + LocalDateTime.now().month + " " + LocalDateTime.now().hour + ":" + LocalDateTime.now().minute
            posts = listOf(
                post.copy(
                    id = nextId++,
                    published = time
                )) + posts
            data.value = posts
            view(nextId)
            sync()
            return
        }



        posts = posts.map{
            if(it.id != post.id) it else it.copy(contentText = post.contentText)
        }
        sync()
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map{
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, quantityLikes =
            if(it.likedByMe) it.quantityLikes-1 else it.quantityLikes+1)
        }
        sync()
        data.value = posts
    }

    override fun repost(id: Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(quantityReposts = it.quantityReposts+1)
        }
        sync()
        data.value = posts
    }

    override fun comment(id: Long) {
        posts = posts.map {
            if(it.id != id) it else it.copy(quantityComments = it.quantityComments+1)
        }
        sync()
        data.value = posts
    }

    override fun view(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(quantityViews = it.quantityViews+1)
        }
        sync()
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        sync()
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
        sync()
        return
    }

    private fun sync(){
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}
