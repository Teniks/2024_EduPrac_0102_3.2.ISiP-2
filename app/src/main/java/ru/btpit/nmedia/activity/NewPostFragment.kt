package ru.btpit.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.FragmentNewPostBinding
import ru.btpit.nmedia.entyties.Post
import ru.btpit.nmedia.entyties.PostViewModel

class NewPostFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, null)
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val parentFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        val viewModel: PostViewModel by viewModels()

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            viewModel.changeContent(
                Post(
                id = 0,
                author = getString(R.string.name_public_group),
                contentText =  binding.edit.text.toString(),
                published = "",
                quantityLikes = 0,
                quantityComments = 0,
                quantityReposts = 0,
                quantityViews = 0
            ))
            viewModel.save()
            findNavController().navigateUp()
        }

        return binding.root
    }

}
