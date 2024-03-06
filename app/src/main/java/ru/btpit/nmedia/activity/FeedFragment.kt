package ru.btpit.nmedia.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import ru.btpit.nmedia.databinding.*;
import ru.btpit.nmedia.entyties.PostViewModel
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.PostEditingCardBinding
import ru.btpit.nmedia.entyties.*
import ru.btpit.nmedia.interfaces.OnInteractionListener

class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, null)

        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        defaultSettings(binding)

        return binding.root
}

    private fun defaultSettings(binding: FragmentFeedBinding){

        val viewModel: PostViewModel by viewModels()

        val adapter =
            PostsAdapter( object: OnInteractionListener {
                val listener = this
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onEdit(post: Post) {
                    val args = Bundle()
                    args.putLong("id", post.id)
                    parentFragmentManager.commit {
                        val postEditingCard = PostEditingCard()
                        postEditingCard.arguments = args
                        replace(R.id.fragmentContainer, postEditingCard)
                        addToBackStack(null)
                    }
                }

                override fun onRemove(post: Post) {
                    viewModel.removeBuId(post.id)
                    parentFragmentManager.commit {
                        replace(R.id.fragmentContainer, FeedFragment())
                    }
                }

                override fun onComment(post: Post) {
                    viewModel.comment(post.id)
                }

                override fun onRepost(post: Post) {
                    viewModel.repost(post.id)

                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.contentText)
                        type = "text/plain"
                    }

                    val contextTime = binding.root.context

                    val shareIntent = Intent.createChooser(intent,
                        ContextCompat.getString(contextTime, R.string.chooser_share_post)
                    )
                    ContextCompat.startActivity(contextTime ,shareIntent, null)
                }

                override fun onView(post: Post) {
                    viewModel.view(post.id)
                }

                override fun onPlayMedia(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(post.urlVideo)
                    }
                    startActivity(intent)
                }

                override fun onNavigateToAlonePost(post: Post) {
                    parentFragmentManager.commit {
                        replace(R.id.fragmentContainer, AlonePostFragment(listener, post))
                        addToBackStack(null)
                    }
                }
            })


        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner){post ->
            adapter.submitList(post)
        }

        viewModel.edited.observe(viewLifecycleOwner){post ->
            if(post.id == 0L){
                return@observe
            }
        }

        binding.fab.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, PostEditingCard())
            }
        }
    }
    object AndroidUtils{
        fun hideKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

