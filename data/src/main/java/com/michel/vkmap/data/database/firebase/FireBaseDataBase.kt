package com.michel.vkmap.data.database.firebase

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michel.vkmap.data.database.IDataBase
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel

class FireBaseDataBase: IDataBase {

    private val dataBase = Firebase.database

    override fun saveLocation(dataPack: LocationDataPackModel){
        dataBase.getReference(GROUP_KEY + "/" + dataPack.userId)
            .setValue(LocationDataModel(
                dataPack.latitude,
                dataPack.longitude
                )
            )
        Log.i("VKMAP", "Firebase saved " + dataPack.latitude + " " + dataPack.longitude)
    }

    companion object{
        private const val GROUP_KEY = "USERS"
    }

}