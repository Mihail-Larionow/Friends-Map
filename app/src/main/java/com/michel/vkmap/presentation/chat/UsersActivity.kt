package com.michel.vkmap.presentation.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.michel.vkmap.R

class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            DialogsActivity.startFrom(this)
        }
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, UsersActivity::class.java)
            context.startActivity(intent)
        }
    }
}