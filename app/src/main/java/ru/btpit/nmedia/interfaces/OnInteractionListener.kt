package ru.btpit.nmedia.interfaces

import ru.btpit.nmedia.entyties.Post

interface OnInteractionListener{
    fun onLike(post: Post) {}
    fun onComment(post: Post) {}
    fun onRepost(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onView(post: Post) {}
    fun onPlayMedia(post: Post) {}
    fun onNavigateToAlonePost(post: Post) {}
}