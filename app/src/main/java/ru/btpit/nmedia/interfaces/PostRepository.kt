package ru.btpit.nmedia.interfaces

import androidx.lifecycle.LiveData
import ru.btpit.nmedia.entyties.Post

interface PostRepository{
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repost(id: Long)
    fun comment(id: Long)
    fun view(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun edit(post: Post, newText: String)
}