package com.michel.vkmap.data.api

interface IApi {

    fun photoRequest(userId: String): String

    fun friendsListRequest()

}