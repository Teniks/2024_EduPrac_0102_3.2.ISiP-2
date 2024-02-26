package ru.btpit.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        defaultSettings()
    }

    fun defaultSettings(){
        var like: Boolean = false
        val likeButton: ImageButton = findViewById(R.id.like)
        val liketext: TextView = findViewById(R.id.textLike)

        var likesCount: Int = 999
        var commentsCount: Int = Random.nextInt(1, 100000)
        var repostsCount: Int = Random.nextInt(1, 10000)

        liketext.setText(convertForm(likesCount))

        likeButton.setOnClickListener {
            if(!like){
                likeButton.setImageResource(R.drawable.like_active)
                val number: Int = ++likesCount
                liketext.setText(convertForm(number))
                like = true
            }else{
                likeButton.setImageResource(R.drawable.like_negative)
                val number: Int = --likesCount
                liketext.setText(convertForm(number))
                like = false
            }
        }

        val commentButton: ImageButton = findViewById(R.id.comment)
        val commentText: TextView = findViewById(R.id.textComment)

        commentText.setText(convertForm(commentsCount))

        commentButton.setOnClickListener {
            val number: Int = commentsCount++
            commentText.setText(convertForm(number))
        }

        val repostButton: ImageButton = findViewById(R.id.repost)
        val repostText: TextView = findViewById(R.id.textRepost)

        repostText.setText(convertForm(repostsCount))

        repostButton.setOnClickListener {
            val number: Int = repostsCount++
            repostText.setText(convertForm(number))
        }
    }
    fun convertForm(x: Int): String{
        var result: String = ""
        if(x > 999 && x < 999999){
            result = (x / 1000).toString() + "K"
        }else if(x > 999999){
            result = (x / 1000000).toString() + "M"
        }else{
            result = x.toString()
        }

        return result
    }

}

