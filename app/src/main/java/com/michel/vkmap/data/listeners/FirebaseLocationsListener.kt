package com.michel.vkmap.data.listeners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.michel.vkmap.domain.models.FirebaseLocationDataModel
import com.michel.vkmap.domain.models.LocationDataModel

class FirebaseLocationsListener: ValueEventListener {

    private val data: MutableLiveData<LocationDataModel> = MutableLiveData()

    override fun onDataChange(snapshot: DataSnapshot) {

        val id = snapshot.key
        Log.v("VKMAP", "Firebase ${id} data changed")
        val pack = snapshot.getValue<FirebaseLocationDataModel>()
        pack?.let {
            if (it.latitude != null && it.longitude != null && it.date != null && id != null) {
                    data.postValue(
                        LocationDataModel(
                            date = it.date,
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    )
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("VKMAP", "Location listening cancelled")
    }

    fun getData(): LiveData<LocationDataModel> {
        return data
    }

}