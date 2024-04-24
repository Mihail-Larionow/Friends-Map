package com.michel.vkmap.presentation.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.michel.vkmap.R
import com.michel.vkmap.presentation.map.MapActivity

class DialogsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)
        Log.v("VKMAP", "DialogsActivity created")

        findViewById<ImageView>(R.id.addButton).setOnClickListener {
            UsersActivity.startFrom(this)
        }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            MapActivity.startFrom(this)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("VKMAP", "DialogsActivity destroyed")
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, DialogsActivity::class.java)
            context.startActivity(intent)
        }
    }
}