package com.michel.vkmap.data.listeners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FirebaseConversationListener: ValueEventListener  {

    private val data: MutableLiveData<ArrayList<String>> = MutableLiveData()

    override fun onDataChange(snapshot: DataSnapshot) {
        val id = snapshot.key
        Log.v("VKMAP", "Firebase ${id} conversation data changed")


    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("VKMAP", "Conversation listening cancelled")
    }

    fun getData(): LiveData<ArrayList<String>> {
        return data
    }
}