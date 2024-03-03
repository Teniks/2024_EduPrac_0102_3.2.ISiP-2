package ru.btpit.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import ru.btpit.nmedia.databinding.ActivityMainBinding
import ru.btpit.nmedia.entyties.PostViewModel
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.PostEditingCardBinding
import ru.btpit.nmedia.entyties.*
import ru.btpit.nmedia.interfaces.OnInteractionListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val bindingEditPost = PostEditingCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        defaultSettings(binding, bindingEditPost)

}

    private fun defaultSettings(binding: ActivityMainBinding, bindingEditPost: PostEditingCardBinding){

        val viewModel: PostViewModel by viewModels()

        val adapter =
            PostsAdapter( object: OnInteractionListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onEdit(post: Post) {
                    with(bindingEditPost){
                        setContentView(root)

                        textFromPost.setText(post.contentText)
                        fab.setOnClickListener {
                            if(textFromPost.text.isNotBlank()){
                                viewModel.edit(post.copy(contentText = textFromPost.text.toString()))
                                viewModel.save()
                            }
                            setContentView(binding.root)
                        }
                    }
                }

                override fun onRemove(post: Post) {
                    viewModel.removeBuId(post.id)
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
            }
        }

        binding.fab.setOnClickListener{
            with(bindingEditPost) {
                setContentView(root)

                fab.setOnClickListener {
                    if(textFromPost.text.isNotBlank()){
                        createNewPost(textFromPost.text.toString(), viewModel)
                    }
                    textFromPost.text.clear()
                    setContentView(binding.root)
                }
            }
        }

        binding.cancelmark.setOnClickListener {
            with(binding.content){
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.groupEditing.visibility = View.GONE
                binding.fab.visibility = View.VISIBLE
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
                createNewPost(text.toString(), viewModel)
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }

    private fun createNewPost(text: String, viewModel:PostViewModel){
        viewModel.changeContent(
            Post(
                id = 0,
                author = getString(R.string.name_public_group),
                contentText = text,
                published = "",
                quantityLikes = 0,
                quantityComments = 0,
                quantityReposts = 0,
                quantityViews = 0
            ))
        viewModel.save()
    }

    object AndroidUtils{
        fun hideKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

