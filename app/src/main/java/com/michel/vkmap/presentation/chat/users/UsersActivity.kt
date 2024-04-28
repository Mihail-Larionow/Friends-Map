package com.michel.vkmap.presentation.chat.users

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
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.models.UserItemModel
import com.michel.vkmap.presentation.chat.adapters.UsersRecyclerAdapter
import com.michel.vkmap.presentation.chat.dialogs.DialogsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersActivity : AppCompatActivity() {

    private lateinit var errorText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val usersList: ArrayList<UserItemModel> = arrayListOf()

    private val viewModel by viewModel<UsersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            DialogsActivity.startFrom(this)
        }

        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)

        recyclerView = findViewById(R.id.recyclerView)
        val adapter = UsersRecyclerAdapter(context = this, usersList = usersList)
        recyclerView.adapter = adapter

        viewModel.friendsList.observe(this){ list ->
            list.forEach {
                id -> addUserToList(userId = id)
                Log.v("VKMAP", "$usersList ${usersList.size}")
                adapter.notifyItemInserted(usersList.size - 1)
            }
        }

        viewModel.networkState.observe(this){ state ->
            setNetworkState(state = state)
        }
    }

    private fun addUserToList(userId: String){
        viewModel.getUserInfo(userId).observe(this){
            Log.v("VKMAP", "$it")
            usersList.add(
                UserItemModel(
                    id = userId,
                    fullName = it.first,
                    photo = it.second
                )
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

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, UsersActivity::class.java)
            context.startActivity(intent)
        }
    }
}