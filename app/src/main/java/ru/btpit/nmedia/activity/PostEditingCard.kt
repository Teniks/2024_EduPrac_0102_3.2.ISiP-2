package ru.btpit.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.PostEditingCardBinding
import ru.btpit.nmedia.entyties.Post
import ru.btpit.nmedia.entyties.PostViewModel
import ru.btpit.nmedia.interfaces.OnInteractionListener
import java.io.Serializable


class PostEditingCard: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostEditingCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}