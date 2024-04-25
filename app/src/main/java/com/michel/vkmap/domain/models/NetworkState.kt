package com.michel.vkmap.domain.models

class NetworkState {

    companion object{
        val LOADED: NetworkState = NetworkState()
        val LOADING: NetworkState = NetworkState()
        val ERROR: NetworkState = NetworkState()
    }

}