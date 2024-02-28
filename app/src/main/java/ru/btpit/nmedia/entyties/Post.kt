package ru.btpit.nmedia.entyties

data class Post(
    val id: Long,
    val author: String,
    val contentText: String,
    val contentPath: Int,
    val published: String,
    var quantityLikes: Int,
    var quantityComments: Int,
    var quantityReposts: Int,
    var quantityViews: Int,
    var likedByMe: Boolean = false
)