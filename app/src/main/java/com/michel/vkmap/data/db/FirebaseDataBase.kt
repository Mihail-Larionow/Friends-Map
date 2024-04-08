package com.michel.vkmap.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michel.vkmap.data.listeners.FirebaseListener
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.data.models.MessageDataModel
import com.michel.vkmap.data.models.MessageDataPackModel

class FirebaseDataBase {

    private val dataBase = Firebase.database

    fun startListening(friends: ArrayList<String>): LiveData<Map<String, LocationModel>> {
        val dataListener = FirebaseListener(friends = friends)
        dataBase.getReference(USERS_KEY).addValueEventListener(dataListener)
        return dataListener.getData()
    }

    fun saveLocation(dataPack: LocationDataPackModel) {
        dataBase.getReference(USERS_KEY + "/" + dataPack.userId)
            .setValue(
                LocationDataModel(
                    dataPack.latitude,
                    dataPack.longitude
                )
            )
        Log.i("VKMAP", "Firebase saved " + dataPack.latitude + " " + dataPack.longitude)
    }

    fun saveMessage(dataPack: MessageDataPackModel) {
        dataBase.getReference(MESSAGES_KEY + "/" + dataPack.text)
            .setValue(
                MessageDataModel(
                    dataPack.text,
                    dataPack.senderId
                )
            )
        Log.i("VKMAP", "Firebase saved " + dataPack.text + " " + dataPack.senderId)
    }

    companion object {
        private const val USERS_KEY = "USERS"
        private const val MESSAGES_KEY = "MESSAGES"
    }
}