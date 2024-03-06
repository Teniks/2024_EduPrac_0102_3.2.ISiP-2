package ru.btpit.nmedia.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.FragmentAlonePostBinding
import ru.btpit.nmedia.entyties.Post
import ru.btpit.nmedia.interfaces.OnInteractionListener
import ru.btpit.nmedia.processing.convertForm
import ru.btpit.nmedia.processing.getImageVideo


class AlonePostFragment(private val listener: OnInteractionListener,
                        private val post: Post) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlonePostBinding.inflate(inflater, container, false)

        binding.apply {
            author.text = post.author
            textFromPost.text = post.contentText
            val image = post.contentPath?.let { ContextCompat.getDrawable(imagePost.context, it) }
            if(image != null){
                if(imagePost.visibility == View.GONE)
                    imagePost.visibility = View.VISIBLE
                imagePost.setImageDrawable(image)
            }else{
                imagePost.visibility = View.GONE
                imagePost.requestLayout()
            }
            published.text = post.published
            like.text = convertForm(post.quantityLikes)
            comment.text = convertForm(post.quantityComments)
            repost.text = convertForm(post.quantityReposts)
            textViews.text = convertForm(post.quantityViews)
            if(post.urlVideo != null){
                videoImage.visibility = View.VISIBLE

                val imageURL = getImageVideo(post.urlVideo)

                Glide.with(videoImage.context)
                    .asBitmap()
                    .load(imageURL)
                    .placeholder(R.drawable.youtubedefault)
                    .error(R.drawable.youtubedefault)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(videoImage)
            }

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

            videoImage.setOnClickListener {
                listener.onPlayMedia(post)
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

        return binding.root
    }
}