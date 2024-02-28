package ru.btpit.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import ru.btpit.nmedia.databinding.ActivityMainBinding
import ru.btpit.nmedia.entyties.Post
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        defaultSettings(binding)
    }

    fun defaultSettings(binding: ActivityMainBinding){

        val firstPost: Post =
            Post(1,
                findViewById<TextView>(R.id.author).text.toString(),
                findViewById<TextView>(R.id.textFromPost).text.toString(),
                R.mipmap.im_wagon_foreground,
                findViewById<TextView>(R.id.published).text.toString(),
                999,
                Random.nextInt(1, 100000),
                Random.nextInt(1, 10000),
                Random.nextInt(1, 5_000_000)
            )

        with(binding){
            author.text = firstPost.author
            textFromPost.text = firstPost.contentText

            published.text = firstPost.published
            textLike.text = firstPost.quantityLikes.toString()
            textComment.text = firstPost.quantityComments.toString()
            textRepost.text = firstPost.quantityReposts.toString()
            textViews.text = firstPost.quantityViews.toString()

            textLike.setText(convertForm(firstPost.quantityLikes))
            if(firstPost.likedByMe){
                like.setImageResource(R.drawable.like_active)
            }

            like.setOnClickListener {
                if(!firstPost.likedByMe){
                    like.setImageResource(R.drawable.like_active)
                    val number: Int = ++firstPost.quantityLikes
                    textLike.setText(convertForm(number))
                    firstPost.likedByMe = true
                }else{
                    like.setImageResource(R.drawable.like_negative)
                    val number: Int = --firstPost.quantityLikes
                    textLike.setText(convertForm(number))
                    firstPost.likedByMe = false
                }
            }

            textComment.setText(convertForm(firstPost.quantityComments))
            comment.setOnClickListener {
                val number: Int = firstPost.quantityComments++
                textComment.setText(convertForm(number))
            }

            textRepost.setText(convertForm(firstPost.quantityReposts))
            repost.setOnClickListener {
                val number: Int = firstPost.quantityReposts++
                textRepost.setText(convertForm(number))
            }

            root.setOnClickListener{
                Toast.makeText(applicationContext, "root", Toast.LENGTH_SHORT).show()
            }

            avatarImageView.setOnClickListener{
                //Toast.makeText(applicationContext, "avatar", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun convertForm(x: Int): String{
        var result: String = ""
        when(x){
            in 0..999-> result = x.toString()
            in 999..9_999-> result = ((x/100).toDouble()/10).toString()  + "K"
            in 10_000..999_999-> result = (x/1_000).toString() + "K"
            in 1_000_000..9_999_999-> result = ((x/100_000).toDouble()/10).toString() + "M"
            in 10_000_000..Int.MAX_VALUE -> result = (x/1_000_000).toString() + "M"
        }

        return result
    }

}

