package ru.btpit.nmedia.entyties

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.btpit.nmedia.R
import ru.btpit.nmedia.interfaces.PostRepository
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.random.Random


class PostRepositoryInMemoryImpl: PostRepository {

    private var posts = listOf(
        Post(
        id = 1,
        author = "Doctor TX - A rascal and fool",
        contentText = "My second completed training work.",
        published = "28 февраля в 21:33",
        quantityLikes = 999,
        quantityComments = Random.nextInt(1, 10_000),
        quantityReposts = Random.nextInt(1, 100_000),
        quantityViews = Random.nextInt(1, 1_000_000),
        contentPath = R.drawable.second
        ),
        Post(
            id = 2,
            author = "Doctor TX - A rascal and fool",
            contentText = "My first completed training work.",
            published = "28 февраля в 19:22",
            quantityLikes = 999,
            quantityComments = Random.nextInt(1, 10_000),
            quantityReposts = Random.nextInt(1, 100_000),
            quantityViews = Random.nextInt(1, 1_000_000),
            contentPath = R.drawable.first
        )
    )
    private val data = MutableLiveData(posts.toList())
    override fun getAll(): LiveData<List<Post>> = data

    private var lastId: Long = posts.size.toLong()
    private fun nextId(): Long {
        lastId++
        return lastId
    }

    override fun save(post: Post){
        if(post.id == 0L){
            val time = LocalDateTime.now().dayOfMonth.toString() + " " + LocalDateTime.now().month + " " + LocalDateTime.now().hour + ":" + LocalDateTime.now().minute
            posts = listOf(
                post.copy(
                    id = nextId(),
                    published = time
                )) + posts
            data.value = posts
            view(lastId)
            return
        }



        posts = posts.map{
            if(it.id != post.id) it else it.copy(contentText = post.contentText)
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