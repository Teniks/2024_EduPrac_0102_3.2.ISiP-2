package ru.btpit.nmedia.entyties

import androidx.fragment.app.Fragment
import ru.btpit.nmedia.R

class WallFragment: Fragment(R.layout.fragment_wall) {
    private val userId: Long by lazy {
        requireArguments().getLong("userId")
    }
}