package com.michel.vkmap.di

import com.michel.vkmap.domain.manager.ILocationManager
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.map.Manager
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.domain.map.Listener
import com.michel.vkmap.domain.usecases.DisplayMapUseCase
import com.vk.api.sdk.VK
import com.yandex.mapkit.location.LocationListener
import org.koin.dsl.module

val domainModule = module {

    factory<GetLocationUseCase> {
        GetLocationUseCase(iUserRepository = get())
    }

    factory<SaveLocationUseCase> {
        SaveLocationUseCase(
            iUserRepository = get(),
            userId = VK.getUserId().toString())
    }


    single<LocationListener>{
        Listener(saveLocationUseCase = get())
    }

    single<ILocationManager>{
        Manager(locationListener = get())
    }


    single<TrackLocationUseCase> {
        TrackLocationUseCase(iLocationManager = get())
    }

    single<DisplayMapUseCase> {
        DisplayMapUseCase(iMap = get())
    }

}