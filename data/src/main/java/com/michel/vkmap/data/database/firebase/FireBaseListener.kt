package com.michel.vkmap.data.database.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.michel.vkmap.data.models.FirebaseDataModel
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.usecases.GetFriendsListUseCase
import com.michel.vkmap.domain.usecases.UpdateMapUseCase
import com.vk.api.sdk.VK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FireBaseListener(
    private val updateMapUseCase: UpdateMapUseCase,
    private val getFriendsListUseCase: GetFriendsListUseCase
): ValueEventListener {

    private lateinit var friends: ArrayList<String>
    init{
        runBlocking{
            launch{
                friends = withContext(Dispatchers.IO){
                    getFriendsListUseCase.execute(VK.getUserId().toString())
                }
            }
        }
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        Log.v("VKMAP", "Firebase data changed")

        snapshot.children.forEach lit@{ dataPack ->
            val userId = dataPack.key
            if(userId !in friends) return@lit
            val data = dataPack.getValue<FirebaseDataModel>()
            Log.d("VKMAP", "data: " + dataPack.key)
            data?.let{
                if(it.latitude != null && it.longitude != null && userId != null){
                    val location = LocationModel(it.latitude, it.longitude)
                    updateMapUseCase.execute(location, userId)
                }
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("VKMAP", "Firebase data cancelled")
    }

}