package ru.btpit.nmedia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import ru.btpit.nmedia.databinding.ActivityMainBinding
import ru.btpit.nmedia.entyties.PostViewModel
import androidx.lifecycle.ViewModel
import androidx.activity.viewModels
import ru.btpit.nmedia.databinding.*;
import ru.btpit.nmedia.entyties.*;
import ru.btpit.nmedia.interfaces.OnInteractionListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        defaultSettings(binding)
    }

    private fun defaultSettings(binding: ActivityMainBinding){

        val viewModel: PostViewModel by viewModels()
        val adapter: PostsAdapter =
            PostsAdapter( object: OnInteractionListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeBuId(post.id)
                }

                override fun onComment(post: Post) {
                    viewModel.comment(post.id)
                }

                override fun onRepost(post: Post) {
                    viewModel.repost(post.id)
                }

                override fun onView(post: Post) {
                    viewModel.view(post.id)
                }
            })

        binding.list.adapter = adapter
        viewModel.data.observe(this){post ->
            adapter.submitList(post)
        }

        viewModel.edited.observe(this){post ->
            if(post.id == 0L){
                return@observe
            }
            with(binding.content){
                requestFocus()
                setText(post.contentText)

                binding.groupEditing.visibility = View.VISIBLE
            }
        }

        binding.cancelmark.setOnClickListener {
            with(binding.content){
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.groupEditing.visibility = View.GONE
            }
        }

        binding.checkmark.setOnClickListener {
            with(binding.content){
                if(text.isNullOrBlank()){
                    Toast.makeText(
                        this@MainActivity,
                        "Content cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(
                    Post(
                        id = 0,
                        author = getString(R.string.name_public_group),
                        contentText = text.toString(),
                        published = "",
                        quantityLikes = 0,
                        quantityComments = 0,
                        quantityReposts = 0,
                        quantityViews = 0
                    ))
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
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

