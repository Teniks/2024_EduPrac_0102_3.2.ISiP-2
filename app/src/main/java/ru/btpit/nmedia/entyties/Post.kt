package ru.btpit.nmedia.entyties

import kotlinx.serialization.Serializable


@Serializable
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
) : java.io.Serializable