package com.michel.vkmap.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michel.vkmap.data.listeners.FirebaseListener
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.data.models.MessageDataModel
import com.michel.vkmap.data.models.MessageDataPackModel

class FirebaseDataBase: IDataBase {

    companion object {
        private const val USERS_KEY = "USERS"
        private const val MESSAGES_KEY = "MESSAGES"
    }

    private val dataBase = Firebase.database

    override fun startListening(friendsVK: ArrayList<String>): MutableLiveData<Map<String, LiveData<LocationDataModel>>>{
        val friendsData:  MutableLiveData<Map<String, LiveData<LocationDataModel>>> = MutableLiveData()
        dataBase.getReference(USERS_KEY).get().addOnSuccessListener { data ->
            val dataMap: MutableMap<String, LiveData<LocationDataModel>> = mutableMapOf()
            data.children.forEach {
                val friendId = it.key
                if(friendId != null && friendId in friendsVK) {
                    dataMap[friendId] = addListener(friendId)
                }
            }
            friendsData.postValue(dataMap)
        }.addOnFailureListener{
            Log.e("VKMAP", "Error getting data", it)
        }
        return friendsData
    }

    private fun addListener(friendId: String): LiveData<LocationDataModel>{
        val dataListener = FirebaseListener()
        dataBase.getReference(USERS_KEY).child(friendId).addValueEventListener(dataListener)
        return dataListener.getData()
    }


    override fun saveLocation(dataPack: LocationDataPackModel) {
        dataBase.getReference(USERS_KEY + "/" + dataPack.userId)
            .setValue(
                dataPack.data
            )
        Log.i("VKMAP", "Firebase saved ${dataPack.data}")
    }

    override fun saveMessage(dataPack: MessageDataPackModel) {
        dataBase.getReference(MESSAGES_KEY + "/" + dataPack.text)
            .setValue(
                MessageDataModel(
                    dataPack.text,
                    dataPack.senderId
                )
            )
        Log.i("VKMAP", "Firebase saved " + dataPack.text + " " + dataPack.senderId)
    }

}