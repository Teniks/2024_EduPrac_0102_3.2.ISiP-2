package ru.btpit.nmedia.entyties

import android.graphics.drawable.Drawable

data class Post(
    val id: Long,
    val author: String,
    val contentText: String,
    val published: String,
    val quantityLikes: Int,
    val quantityComments: Int,
    val quantityReposts: Int,
    val quantityViews: Int,
    val contentPath: Int? = null,
    val likedByMe: Boolean = false
)