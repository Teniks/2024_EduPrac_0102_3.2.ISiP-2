package ru.btpit.nmedia.activity;

import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.CardPostBinding

class PostCardFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding = CardPostBinding.inflate(inflater, container, false)

        return binding.root
    }
}