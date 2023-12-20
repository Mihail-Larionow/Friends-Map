package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    init{
        Log.d("MainViewModel", "init()")
    }

    override fun onCleared() {
        Log.d("MainViewModel", "onCleared()")
        super.onCleared()
    }

}