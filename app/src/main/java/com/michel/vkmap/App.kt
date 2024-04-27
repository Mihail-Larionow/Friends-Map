package com.michel.vkmap

import android.app.Application
import android.util.Log
import com.michel.vkmap.di.appModule
import com.michel.vkmap.di.chatDI
import com.michel.vkmap.di.mapDI

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.vk.api.sdk.utils.VKUtils.getCertificateFingerprint
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App: Application() {

    override fun onCreate(){
        super.onCreate()

        Log.i("VKMAP", "APPLICATION STARTED")

        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY)
        MapKitFactory.initialize(this)

        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, mapDI, chatDI))
        }

        VK.addTokenExpiredHandler(tokenTracker)
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            // token expired
        }
    }
}