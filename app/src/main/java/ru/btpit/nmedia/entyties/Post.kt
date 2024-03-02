package ru.btpit.nmedia.entyties

import android.graphics.drawable.Drawable
import kotlinx.serialization.Serializable

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
    val urlVideo: String? = null,
    val likedByMe: Boolean = false
)