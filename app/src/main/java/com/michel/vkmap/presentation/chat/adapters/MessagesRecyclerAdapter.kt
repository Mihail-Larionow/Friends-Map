package com.michel.vkmap.presentation.chat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michel.vkmap.R
import com.michel.vkmap.domain.models.MessageItemModel
import com.michel.vkmap.extensions.setIconByteArray

class MessagesRecyclerAdapter(
    private val list: ArrayList<MessageItemModel>,
    private val userId: String
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val SENT_MESSAGE_TYPE = 100
        const val RECEIVED_MESSAGE_TYPE = 200
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if(viewType == SENT_MESSAGE_TYPE){
            val view = layoutInflater.inflate(R.layout.item_sent_messages_list, parent, false)
            SentMessageItemViewHolder(view = view)
        } else{
            val view = layoutInflater.inflate(R.layout.item_received_messages_list, parent, false)
            ReceivedMessageItemViewHolder(view = view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].senderId == userId) {
            SENT_MESSAGE_TYPE
        } else {
            RECEIVED_MESSAGE_TYPE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        if(holder is SentMessageItemViewHolder) holder.bind(message = message)
        else if(holder is ReceivedMessageItemViewHolder) holder.bind(message = message)
    }

}

class SentMessageItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(message: MessageItemModel){
        view.findViewById<TextView>(R.id.messageText).text = message.text
    }
}

class ReceivedMessageItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(message: MessageItemModel){
        view.findViewById<TextView>(R.id.messageText).text = message.text
        view.findViewById<ImageView>(R.id.senderPhoto).setIconByteArray(message.photo)
    }
}


