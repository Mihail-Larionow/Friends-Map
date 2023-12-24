package com.michel.vkmap

import android.app.Application
import com.michel.vkmap.di.appModule
import com.michel.vkmap.di.dataModule
import com.michel.vkmap.di.domainModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate(){
        super.onCreate()

        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY)

        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}