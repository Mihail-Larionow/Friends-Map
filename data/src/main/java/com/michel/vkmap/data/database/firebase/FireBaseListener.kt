package com.michel.vkmap.data.database.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.michel.vkmap.data.models.FirebaseDataModel
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.LocationPackModel
import com.michel.vkmap.domain.usecases.MovePlaceMarkUseCase
import com.michel.vkmap.domain.usecases.UpdateLocationUseCase

class FireBaseListener(private val updateLocationUseCase: UpdateLocationUseCase): ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach{ dataPack ->
            val userId = dataPack.key
            val data = dataPack.getValue<FirebaseDataModel>()
            Log.d("VKMAP", "data: " + dataPack.key)
            data?.let{
                if(it.latitude != null && it.longitude != null && userId != null){
                    val location = LocationModel(it.latitude, it.longitude)
                    updateLocationUseCase.execute(locationModel = location, userId)
                    }
            }

        }


        //updateLocationUseCase.execute()
        Log.e("VKMAP", "Firebase data changed")
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("VKMAP", "Firebase data cancelled")
    }

}