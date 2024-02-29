package ru.btpit.nmedia.entyties

import android.content.Context
import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun comment(id: Long) = repository.comment(id)
    fun repost(id: Long) = repository.repost(id)
    fun view(id: Long) = repository.view(id)
}