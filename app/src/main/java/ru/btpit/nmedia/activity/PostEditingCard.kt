package ru.btpit.nmedia.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.btpit.nmedia.databinding.PostEditingCardBinding


class PostEditingCard: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostEditingCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}