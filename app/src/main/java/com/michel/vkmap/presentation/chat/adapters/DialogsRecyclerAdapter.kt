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
import com.michel.vkmap.domain.models.ConversationItemModel
import com.michel.vkmap.extensions.setIconByteArray
import com.michel.vkmap.presentation.chat.conversation.ConversationActivity

class DialogsRecyclerAdapter(
    private val context: Context,
    private val list: ArrayList<ConversationItemModel>
): RecyclerView.Adapter<ConversationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationItemViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_conversations_list, parent, false)
        return ConversationItemViewHolder(view = view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ConversationItemViewHolder, position: Int) {
        holder.bind(list[position], context = context)
    }

}

class ConversationItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(conversation: ConversationItemModel, context: Context){
        view.findViewById<TextView>(R.id.conversationName).text = conversation.title

        view.findViewById<TextView>(R.id.lastMessage).text = conversation.message

        val photo = conversation.photo
        view.findViewById<ImageView>(R.id.conversationPhoto)
            .setIconByteArray(photo)

        view.setOnClickListener{
            val intent = Intent(context, ConversationActivity::class.java)
            intent.putExtra("conversationId", conversation.id)
            context.startActivity(intent)
        }
    }
}