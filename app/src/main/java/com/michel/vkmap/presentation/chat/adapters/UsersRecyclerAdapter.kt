package com.michel.vkmap.presentation.chat.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.michel.vkmap.R
import com.michel.vkmap.domain.models.UserModel
import com.michel.vkmap.presentation.chat.Conversation

class UsersRecyclerAdapter(
    private val context: Context,
    private val usersList: ArrayList<UserModel>
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
        (holder as UserItemViewHolder).bind(usersList[position], context = context)
    }

}

class UserItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(user: UserModel, context: Context){
        view.findViewById<TextView>(R.id.userName).text = user.fullName

        val photo = user.photo
        view.findViewById<ImageView>(R.id.userPhoto)
            .setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.size))
        
        view.setOnClickListener{
            val intent = Intent(context, Conversation::class.java)
            //intent.putExtra("productId", user?.id)
            context.startActivity(intent)
        }
    }
}