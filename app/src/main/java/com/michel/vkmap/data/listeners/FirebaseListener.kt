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
import com.michel.vkmap.domain.models.LocationModel

class FirebaseListener(): ValueEventListener {

    private val data: MutableLiveData<LocationDataModel> = MutableLiveData()

    override fun onDataChange(snapshot: DataSnapshot) {

        val id = snapshot.key
        Log.v("VKMAP", "Firebase ${id} data changed")
        val pack = snapshot.getValue<FirebaseLocationDataModel>()
        pack?.let {
            if (it.location != null && it.date != null && id != null) {
                if (it.location.latitude != null && it.location.longitude != null)
                    data.postValue(
                        LocationDataModel(
                            date = it.date,
                            location = LocationModel(
                                latitude = it.location.latitude,
                                longitude = it.location.longitude
                            )
                        )
                    )
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {

    }

    fun getData(): LiveData<LocationDataModel> {
        return data
    }

}