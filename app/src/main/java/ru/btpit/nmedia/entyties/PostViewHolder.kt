package ru.btpit.nmedia.entyties

import android.content.res.Resources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.CardPostBinding
import ru.btpit.nmedia.processing.convertForm

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener,
    private val onCommentListener: OnCommentListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            textFromPost.text = post.contentText
            if(post.contentPath != null){
                val image = ContextCompat.getDrawable(imagePost.context, post.contentPath)
                if(image != null){
                    imagePost.setImageDrawable(image)
                }else{
                    binding.imagePost.layoutParams.height = 1
                    binding.imagePost.requestLayout()
                }
            }else{
                binding.imagePost.layoutParams.height = 0
                binding.imagePost.requestLayout()
            }
            published.text = post.published
            textLike.text = convertForm(post.quantityLikes)
            textComment.text = convertForm(post.quantityComments)
            textRepost.text = convertForm(post.quantityReposts)
            textViews.text = convertForm(post.quantityViews)

            like.setImageResource(
                if(post.likedByMe) R.drawable.like_active else R.drawable.like_negative
            )
            like.setOnClickListener{
                onLikeListener(post)
            }

            comment.setOnClickListener{
                onCommentListener(post)
            }
            repost.setOnClickListener{
                onRepostListener(post)
            }
        }
    }
}