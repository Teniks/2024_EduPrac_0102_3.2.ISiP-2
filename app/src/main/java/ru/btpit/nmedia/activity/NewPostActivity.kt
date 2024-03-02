package ru.btpit.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.btpit.nmedia.R
import ru.btpit.nmedia.databinding.ActivityNewPostBinding
import ru.btpit.nmedia.databinding.IntentExplicitActivityBinding

class NewPostActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            if(binding.edit.text.isBlank()){
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                //Важно: putExtra определяет имā ключа, по которому нужно положить
                //значение. Не обāзательно это должно быть одно из статических
                //полей класса Intent. Вы можете сделать собственное значение
                //(например, с помощью companion object), главное, чтобы оба Activity
                //договорились, какое
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

}
