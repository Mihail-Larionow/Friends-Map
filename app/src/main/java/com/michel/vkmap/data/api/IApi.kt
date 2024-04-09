package com.michel.vkmap.data.api

import androidx.lifecycle.LiveData

interface IApi {

    fun photoUrlRequest(userId: String): LiveData<ByteArray>

    fun friendsListRequest(userId: String): LiveData<ArrayList<String>>

}