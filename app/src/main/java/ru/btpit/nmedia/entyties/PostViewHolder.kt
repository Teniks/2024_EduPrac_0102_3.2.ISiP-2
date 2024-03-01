package ru.btpit.nmedia.entyties

import android.content.res.Resources
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.CardPostBinding
import ru.btpit.nmedia.interfaces.OnInteractionListener
import ru.btpit.nmedia.processing.convertForm

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: OnInteractionListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            textFromPost.text = post.contentText
            val image = post.contentPath?.let { ContextCompat.getDrawable(imagePost.context, it) }
            if(image != null){
                if(binding.imagePost.visibility == View.GONE)
                    binding.imagePost.visibility = View.VISIBLE
                imagePost.setImageDrawable(image)
            }else{
                binding.imagePost.visibility = View.GONE
                binding.imagePost.requestLayout()
            }
            published.text = post.published
            like.text = convertForm(post.quantityLikes)
            comment.text = convertForm(post.quantityComments)
            repost.text = convertForm(post.quantityReposts)
            textViews.text = convertForm(post.quantityViews)

            like.isChecked = post.likedByMe
            /*like.setImageResource(
                if(post.likedByMe) R.drawable.like_active else R.drawable.like_negative
            )*/
            //Прежняя и новая реализация

            like.setOnClickListener{
                listener.onLike(post)
            }

            like.isChecked = post.likedByMe
            like.text = convertForm(post.quantityLikes)

            comment.setOnClickListener{
                listener.onComment(post)
            }
            repost.setOnClickListener{
                listener.onRepost(post)
            }

            actionBar.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when(item.itemId){
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true

                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}