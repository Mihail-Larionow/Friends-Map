package com.michel.vkmap.data.listeners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.michel.vkmap.data.models.FirebaseDataModel
import com.michel.vkmap.data.models.LocationModel

class FirebaseListener(private val friends: ArrayList<String>): ValueEventListener {

    private val data: MutableLiveData<Map<String, LocationModel>> = MutableLiveData()

    override fun onDataChange(snapshot: DataSnapshot) {
        Log.v("VKMAP", "Firebase data changed")

        val map: MutableMap<String, LocationModel> = mutableMapOf()

        snapshot.children.forEach lit@{ dataPack ->
            val userId = dataPack.key
            if(userId !in friends) return@lit
            val data = dataPack.getValue<FirebaseDataModel>()
            Log.d("VKMAP", "data: " + dataPack.key)
            data?.let{
                if(it.latitude != null && it.longitude != null && userId != null){
                    val location = LocationModel(it.latitude, it.longitude)
                    map[userId] = location
                }
            }
        }

        data.postValue(map)
    }

    override fun onCancelled(error: DatabaseError) {

    }

    fun getData(): LiveData<Map<String, LocationModel>> {
        return data
    }

}