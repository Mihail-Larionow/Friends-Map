package com.michel.vkmap.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.R
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.presentation.map.MapActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKAuthException

class WelcomeActivity : ComponentActivity() {

    private lateinit var loginButton: Button
    private lateinit var messageText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("VKMAP", "WelcomeActivity created")

        if(VK.isLoggedIn()){
            Log.i("VKMAP",VK.getUserId().toString() + " is logged")
            MapActivity.startFrom(this@WelcomeActivity)
            finish()
        }

        setContentView(R.layout.activity_welcome)

        val authLauncher = VK.login(this) { result: VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> onLoginSuccess()
                is VKAuthenticationResult.Failed -> onLoginFailed(result.exception)
            }
        }

        loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener{
            loginButton.isClickable = false
            authLauncher.launch(arrayListOf(VKScope.FRIENDS, VKScope.PHOTOS))
        }

        messageText = findViewById<TextView>(R.id.messageText)
    }

    private fun onLoginSuccess(){
        Log.i("VKMAP",VK.getUserId().toString() + " passed authorization")
        setNetworkState(NetworkState.LOADED)
    }

    private fun onLoginFailed(exception: VKAuthException){
        Log.w("VKMAP", "User didn't pass authorization")
        Log.e("VKMAP", "$exception")
        setNetworkState(NetworkState.ERROR)
    }

    private fun setNetworkState(state: NetworkState){
        when(state){
            NetworkState.LOADED -> {
                messageText.text = "Успех!"
                messageText.visibility = View.VISIBLE
                MapActivity.startFrom(this@WelcomeActivity)
                finish()
            }
            NetworkState.ERROR -> {
                messageText.text = "Произошла ошибка"
                messageText.visibility = View.VISIBLE
                loginButton.isClickable = true
            }
        }
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

}