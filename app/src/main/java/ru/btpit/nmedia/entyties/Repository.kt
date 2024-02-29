package ru.btpit.nmedia.entyties

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.btpit.nmedia.R
import kotlin.random.Random

interface PostRepository{
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repost(id: Long)
    fun comment(id: Long)
    fun view(id: Long)
}
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

    public fun addListPosts(post: Post){
        val mutableList = posts.toMutableList()
        mutableList.add(post)
        posts = mutableList.toList()
    }

    private val data = MutableLiveData(posts.toList())

    override fun getAll(): LiveData<List<Post>> = data

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

}