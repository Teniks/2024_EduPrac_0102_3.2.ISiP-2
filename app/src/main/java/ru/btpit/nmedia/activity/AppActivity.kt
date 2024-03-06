package ru.btpit.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.ActivityAppBinding

class AppActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //intentHandlerSet(binding)

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, FeedFragment())
        }
    }

    private fun intentHandlerSet(binding: ActivityAppBinding){
        intent?.let {
            if (it.action == Intent.ACTION_SEND){
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()){
                Snackbar.make(binding.root, R.string.error_empty_content, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok){
                        finish()
                    }.show()
                return@let
            }
        }
    }
}