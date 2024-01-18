package com.michel.vkmap.data.database.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.LocationPackModel
import com.michel.vkmap.domain.usecases.MovePlaceMarkUseCase
import com.michel.vkmap.domain.usecases.UpdateLocationUseCase

class FireBaseListener(private val updateLocationUseCase: UpdateLocationUseCase): ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach{
            val data = it.value
            if(data != null){
                
            }
            Log.d("VKMAP", "data: " + data.toString())
        }

        //updateLocationUseCase.execute()
        Log.e("VKMAP", "Firebase data changed")
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("VKMAP", "Firebase data cancelled")
    }

}