package ru.btpit.nmedia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.btpit.nmedia.R;

public class PostCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_post);
    }
}