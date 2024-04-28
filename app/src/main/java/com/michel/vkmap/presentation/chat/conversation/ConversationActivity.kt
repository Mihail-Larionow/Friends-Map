package com.michel.vkmap.presentation.chat.conversation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.michel.vkmap.R
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.presentation.chat.dialogs.DialogsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConversationActivity : AppCompatActivity() {

    private lateinit var errorText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText

    private val viewModel by viewModel<ConversationViewModel>()
    private var conversationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        val userId = intent.getStringExtra("userId")
        conversationId = intent.getStringExtra("conversationId")

        Log.e("VKMAP", "$userId, $conversationId")

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            DialogsActivity.startFrom(this)
        }

        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)
        editText = findViewById(R.id.editText)

        recyclerView = findViewById(R.id.recyclerView)

        findViewById<ImageView>(R.id.sendButton).setOnClickListener {
            sendMessage(editText.text.toString(), userId)
        }

    }

    private fun sendMessage(text: String, userId: String?){
        if(conversationId == null && userId != null) {
            conversationId = viewModel.createConversation(
                usersList = arrayListOf(userId)
            )
        }
        conversationId?.let{
            viewModel.sendMessage(
                conversationId = it,
                text = text
            )
        }
    }

    private fun setNetworkState(state: NetworkState){
        when(state){
            NetworkState.LOADING ->{
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                errorText.visibility = View.GONE
            }
            NetworkState.LOADED -> {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                errorText.visibility = View.GONE
            }
            NetworkState.ERROR -> {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.GONE
                errorText.visibility = View.VISIBLE
            }
        }
    }



}