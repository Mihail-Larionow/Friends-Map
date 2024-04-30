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
import com.michel.vkmap.domain.models.MessageItemModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.presentation.chat.adapters.MessagesRecyclerAdapter
import com.michel.vkmap.presentation.chat.dialogs.DialogsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConversationActivity : AppCompatActivity() {

    private lateinit var errorText: TextView
    private lateinit var emptyText: TextView
    private lateinit var conversationName: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var adapter: MessagesRecyclerAdapter
    private val recyclerList: ArrayList<MessageItemModel> = arrayListOf()
    private val messages: MutableSet<String> = mutableSetOf()


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
        emptyText = findViewById(R.id.emptyText)
        editText = findViewById(R.id.editText)
        conversationName = findViewById(R.id.conversationName)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = MessagesRecyclerAdapter(list = recyclerList, userId = viewModel.id)
        recyclerView.adapter = adapter

        findViewById<ImageView>(R.id.sendButton).setOnClickListener {
            val text = "${editText.text}"
            if(text != "")
                sendMessage(
                    text = text,
                    userId = userId
                )
            editText.text.clear()
        }

        if(conversationId != null){
            conversationId?.let{
                setConversationInfo(conversationId = it)
                startMessagesTracking(conversationId = it)
            }
        }
        else{
            userId?.let{
                setUserInfo(userId = it)
            }
        }
    }

    private fun setConversationInfo(conversationId: String){
        viewModel.getConversationInfo(conversationId = conversationId){ conversation ->
            when (conversation.users.size) {
                1 -> {
                    conversationName.text = "Избранное"
                }
                2 -> {
                    val user = conversation.users.filter { id -> id != viewModel.id }[0]
                    setUserInfo(userId = user)
                }
                else -> {
                    conversationName.text = conversation.title
                }
            }
        }
    }

    private fun setUserInfo(userId: String){
        viewModel.getUserInfo(userId = userId){
            conversationName.text = it.fullName
        }
    }

    private fun sendMessage(text: String, userId: String?){
        if(conversationId == null && userId != null) {
            conversationId = viewModel.createConversation(
                usersList = arrayListOf(userId)
            )
            conversationId?.let{ startMessagesTracking(conversationId = it) }
        }
        conversationId?.let{
            viewModel.sendMessage(
                conversationId = it,
                text = text
            )
        }
    }

    private fun startMessagesTracking(conversationId: String){
        viewModel.startMessagesTracking(conversationId = conversationId).observe(this){ map ->
            Log.v("VKMAP", "observing list $map")
            map.forEach { (_, id) ->
                if(id !in messages){
                    addMessageToList(messageId = id)
                    messages.add(id)
                }
            }
        }
        viewModel.networkState.observe(this){ state ->
            Log.e("VKMAP", "$state")
            setNetworkState(state = state)
        }
    }

    private fun addMessageToList(messageId: String){
        Log.v("VKMAP", "observing message $messageId")

        viewModel.getMessage(messageId){ message ->
            val text = message.text
            val sender = message.senderId
            val createdAt = message.createdAt
            viewModel.getUserInfo(sender){ info ->
                recyclerList.add(
                    MessageItemModel(
                        text = text,
                        createdAt = createdAt,
                        senderId = sender,
                        photo = info.photo
                    )
                )
            }

            recyclerList.sortBy { it.createdAt }
            adapter.notifyItemChanged(0)
            adapter.notifyItemInserted(recyclerList.size - 1)
        }
    }

    private fun setNetworkState(state: NetworkState){
        when(state){
            NetworkState.LOADING ->{
                emptyText.visibility = View.GONE
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                errorText.visibility = View.GONE
            }
            NetworkState.LOADED -> {
                emptyText.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                errorText.visibility = View.GONE
            }
            NetworkState.ERROR -> {
                emptyText.visibility = View.GONE
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.GONE
                errorText.visibility = View.VISIBLE
            }
        }
    }



}