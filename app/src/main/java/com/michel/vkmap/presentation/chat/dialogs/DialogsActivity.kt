package com.michel.vkmap.presentation.chat.dialogs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.michel.vkmap.R
import com.michel.vkmap.domain.models.ConversationItemModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.presentation.chat.adapters.DialogsRecyclerAdapter
import com.michel.vkmap.presentation.chat.users.UsersActivity
import com.michel.vkmap.presentation.map.MapActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogsActivity : AppCompatActivity() {

    private lateinit var errorText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val recyclerList: ArrayList<ConversationItemModel> = arrayListOf()
    private val conversations: MutableSet<String> = mutableSetOf()

    private lateinit var adapter: DialogsRecyclerAdapter

    private val viewModel by viewModel<DialogsViewModel>()

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

        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = DialogsRecyclerAdapter(context = this, list = recyclerList)
        recyclerView.adapter = adapter

        startConversationsTracking()

        viewModel.networkState.observe(this){  state ->
            setNetworkState(state = state)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("VKMAP", "DialogsActivity destroyed")
    }

    private fun startConversationsTracking(){
        viewModel.conversations.observe(this){ map ->
            map.forEach { (_, id) ->
                if(id !in conversations){
                    addConversationToList(conversationId = id)
                    conversations.add(id)
                }
            }
        }
    }

    private fun addConversationToList(conversationId: String){
        viewModel.getConversationInfo(conversationId){ conversation ->
            val user = conversation.users.filter { id -> id != viewModel.id }[0]



            viewModel.getUserInfo(userId = user){ userInfo ->
                viewModel.startMessagesTracking(conversationId = conversationId).observe(this){ messages ->
                    val messageId = messages.values.first()
                    viewModel.getMessage(messageId = messageId){ message ->
                        Log.v("VKMAP", "$message")
                        recyclerList.add(
                            ConversationItemModel(
                                id = conversationId,
                                title = userInfo.fullName,
                                message = message.text,
                                photo = userInfo.photo
                            )
                        )
                        adapter.notifyItemInserted(recyclerList.size - 1)
                    }
                }
            }

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

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, DialogsActivity::class.java)
            context.startActivity(intent)
        }
    }
}