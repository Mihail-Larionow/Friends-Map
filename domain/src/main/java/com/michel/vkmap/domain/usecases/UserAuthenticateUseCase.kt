package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.authentication.IAuthentication

class UserAuthenticateUseCase(private val iAuthentication: IAuthentication) {

    fun execute(){
        Log.d("VKMAP", "UseCase: UserAuthenticate")
        iAuthentication.launch()
    }

}