package ru.btpit.nmedia.interfaces

import ru.btpit.nmedia.entyties.Post

interface PostDAO {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likedById(id: Long) {}
    fun removeById(id: Long) {}
    fun commentById(id: Long) {}
    fun repostById(id: Long) {}
    fun viewById(id: Long) {}
}