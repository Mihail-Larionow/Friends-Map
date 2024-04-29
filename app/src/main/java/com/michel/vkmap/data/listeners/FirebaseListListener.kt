package com.michel.vkmap.data.listeners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class FirebaseListListener: ValueEventListener  {

    private val data: MutableLiveData<Map<String, String>> = MutableLiveData()

    override fun onDataChange(snapshot: DataSnapshot) {
        val map: MutableMap<String, String> = mutableMapOf()
        val pack = snapshot.getValue<Map<String, String>>()
        pack?.let { data ->
            data.forEach { (date, id) -> map[date] = id }
        }
        data.postValue(map.toSortedMap(compareByDescending { it }))
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("VKMAP", "Conversation listening cancelled")
    }

    fun getData(): LiveData<Map<String, String>> {
        return data
    }
}