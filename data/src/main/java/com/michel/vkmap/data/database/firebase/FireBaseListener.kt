package com.michel.vkmap.data.database.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.michel.vkmap.domain.usecases.MovePlaceMarkUseCase

class FireBaseListener(private val movePlaceMarkUseCase: MovePlaceMarkUseCase): ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {
        //movePlaceMarkUseCase.execute()
        Log.e("VKMAP", "Firebase data changed")
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("VKMAP", "Firebase data cancelled")
    }

}