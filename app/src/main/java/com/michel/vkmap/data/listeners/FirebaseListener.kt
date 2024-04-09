package com.michel.vkmap.data.listeners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.michel.vkmap.data.models.FirebaseLocationDataModel
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationModel

class FirebaseListener(private val friends: ArrayList<String>): ValueEventListener {

    private val data: MutableLiveData<Map<String, LocationDataModel>> = MutableLiveData()

    override fun onDataChange(snapshot: DataSnapshot) {
        Log.v("VKMAP", "Firebase data changed")

        val locationsData: MutableMap<String, LocationDataModel> = mutableMapOf()

        snapshot.children.forEach lit@{ dataPack ->
            val userId = dataPack.key
            if(userId !in friends) return@lit
            val data = dataPack.getValue<FirebaseLocationDataModel>()
            Log.d("VKMAP", "data: " + dataPack.key)
            data?.let{
                if(it.location != null && it.date != null && userId != null){
                    if(it.location.latitude != null && it.location.longitude != null)
                        locationsData[userId] = LocationDataModel(
                            date = it.date,
                            location = LocationModel(
                                latitude = it.location.latitude,
                                longitude = it.location.longitude
                            )
                        )
                }
            }
        }

        data.postValue(locationsData)
    }

    override fun onCancelled(error: DatabaseError) {

    }

    fun getData(): LiveData<Map<String, LocationDataModel>> {
        return data
    }

}