package com.michel.vkmap.di

import com.michel.vkmap.domain.manager.ILocationManager
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.map.Manager
import com.michel.vkmap.domain.usecases.StartLocationTrackingUseCase
import com.michel.vkmap.domain.usecases.StopLocationTrackingUseCase
import com.michel.vkmap.domain.map.Listener
import com.yandex.mapkit.location.LocationListener
import org.koin.dsl.module

val domainModule = module {

    factory<GetLocationUseCase> {
        GetLocationUseCase(iUserRepository = get())
    }

    factory<SaveLocationUseCase> {
        SaveLocationUseCase(iUserRepository = get())
    }


    single<LocationListener>{
        Listener(saveLocationUseCase = get())
    }

    single<ILocationManager>{
        Manager(locationListener = get())
    }


    single<StartLocationTrackingUseCase> {
        StartLocationTrackingUseCase(iLocationManager = get())
    }

    single<StopLocationTrackingUseCase> {
        StopLocationTrackingUseCase(iLocationManager = get())
    }


}