package com.michel.vkmap.presentation.chat.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michel.vkmap.R
import com.michel.vkmap.domain.models.UserItemModel
import com.michel.vkmap.extensions.setIconByteArray
import com.michel.vkmap.presentation.chat.conversation.ConversationActivity

class UsersRecyclerAdapter(
    private val context: Context,
    private val usersList: ArrayList<UserItemModel>
): RecyclerView.Adapter<UserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_users_list, parent, false)
        return UserItemViewHolder(view = view)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(usersList[position], context = context)
    }

}

class UserItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(user: UserItemModel, context: Context){
        view.findViewById<TextView>(R.id.userName).text = user.fullName

        val photo = user.photo
        view.findViewById<ImageView>(R.id.userPhoto)
            .setIconByteArray(photo)
        
        view.setOnClickListener{
            val intent = Intent(context, ConversationActivity::class.java)
            intent.putExtra("userId", user.id)
            context.startActivity(intent)
        }
    }
}