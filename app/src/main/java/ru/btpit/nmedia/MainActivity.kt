package ru.btpit.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import ru.btpit.nmedia.databinding.ActivityMainBinding
import ru.btpit.nmedia.entyties.PostViewModel
import androidx.lifecycle.ViewModel
import androidx.activity.viewModels
import ru.btpit.nmedia.databinding.*;
import ru.btpit.nmedia.entyties.*;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        defaultSettings(binding)
    }

    fun defaultSettings(binding: ActivityMainBinding){
        
        val viewModel: PostViewModel by viewModels()
        val adapter: PostsAdapter = PostsAdapter({viewModel.likeById(it.id)}, {viewModel.repost(it.id)}, {viewModel.comment(it.id)})

        binding.list.adapter = adapter
        viewModel.data.observe(this){post ->
            adapter.submitList(post)
        }
    }


}

