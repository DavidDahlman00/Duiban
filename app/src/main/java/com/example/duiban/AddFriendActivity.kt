package com.example.duiban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddFriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}