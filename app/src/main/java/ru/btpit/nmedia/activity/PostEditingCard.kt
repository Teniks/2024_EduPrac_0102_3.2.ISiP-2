package ru.btpit.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.PostEditingCardBinding
import ru.btpit.nmedia.entyties.Post
import ru.btpit.nmedia.entyties.PostViewModel


class PostEditingCard: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, null)
        val binding = PostEditingCardBinding.inflate(inflater, container, false)

        work(binding)

        return binding.root
    }

    private fun work(binding: PostEditingCardBinding) {

        val viewModel: PostViewModel by viewModels()
        val id:Long? = arguments?.getLong("id")
        val post: Post
        if (id != null) {
            val receivedPost: Post = viewModel.data.map { it ->
                it.find {
                    it.id == id
                }
            }.value!!

            binding.textFromPost.setText(receivedPost.contentText)
            post = receivedPost

            with(binding) {
                fab.setOnClickListener {
                    viewModel.edit(post)
                    viewModel.save()

                    returning()
                }
            }
        }else{
            with(binding) {
                fab.setOnClickListener {
                    viewModel.changeContent(
                        Post(
                            id = 0,
                            author = getString(R.string.name_public_group),
                            contentText = textFromPost.text.toString(),
                            published = "",
                            quantityLikes = 0,
                            quantityComments = 0,
                            quantityReposts = 0,
                            quantityViews = 0
                        )
                    )
                    viewModel.save()

                    returning()
                }
            }
        }
    }

    private fun returning(){
        parentFragmentManager.commit {
            replace(R.id.fragmentContainer, FeedFragment())
        }
    }
}