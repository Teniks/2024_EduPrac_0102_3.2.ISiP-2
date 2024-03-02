package ru.btpit.nmedia.activity

import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.IntentExplicitActivityBinding
import ru.btpit.nmedia.entyties.NewPostResultContract
import ru.btpit.nmedia.entyties.Post
import ru.btpit.nmedia.entyties.PostViewModel

class IntentExplicitActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intent_explicit_activity)

        val binding = IntentExplicitActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostResultContract()){ result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(Post(0, "", result, "", 0, 0, 0, 0))
            viewModel.save()
        }

        binding.fab.setOnClickListener{
            newPostLauncher.launch()
        }
    }
}