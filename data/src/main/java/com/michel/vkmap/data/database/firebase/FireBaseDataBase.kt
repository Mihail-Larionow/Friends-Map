package com.michel.vkmap.data.database.firebase

import android.util.Log
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michel.vkmap.data.database.IDataBase
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.data.models.MessageDataModel
import com.michel.vkmap.data.models.MessageDataPackModel

class FireBaseDataBase(dataListener: ValueEventListener): IDataBase {

    private val dataBase = Firebase.database

    init{
        dataBase.getReference(USERS_KEY).addValueEventListener(dataListener)
    }

    override fun saveLocation(dataPack: LocationDataPackModel){
        dataBase.getReference(USERS_KEY + "/" + dataPack.userId)
            .setValue(LocationDataModel(
                dataPack.latitude,
                dataPack.longitude
                )
            )
        Log.i("VKMAP", "Firebase saved " + dataPack.latitude + " " + dataPack.longitude)
    }

    override fun saveMessage(dataPack: MessageDataPackModel) {
        dataBase.getReference(MESSAGES_KEY + "/" + dataPack.text)
            .setValue(MessageDataModel(
                dataPack.text,
                dataPack.senderId
                )
            )
        Log.i("VKMAP", "Firebase saved " + dataPack.text + " " + dataPack.senderId)
    }

    companion object{
        private const val USERS_KEY = "USERS"
        private const val MESSAGES_KEY = "MESSAGES"
    }

}