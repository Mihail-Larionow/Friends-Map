package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.NetworkState
import com.michel.vkmap.domain.repository.IMapRepository

class GetNetworkStateUseCase(private val repository: IMapRepository) {

    fun execute(): LiveData<NetworkState>{
        return repository.getNetworkState()
    }

}