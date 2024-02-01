package com.michel.vkmap.di

import com.michel.vkmap.domain.manager.ILocationManager
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.map.Manager
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.domain.map.YandexListener
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.DisplayMapUseCase
import com.michel.vkmap.domain.usecases.GetFriendsListUseCase
import com.michel.vkmap.domain.usecases.GetPhotosUseCase
import com.michel.vkmap.domain.usecases.MovePlaceMarkUseCase
import com.michel.vkmap.domain.usecases.UpdateMapUseCase
import com.michel.vkmap.domain.usecases.ZoomUseCase
import com.vk.api.sdk.VK
import com.yandex.mapkit.location.LocationListener
import org.koin.dsl.module

val userId = VK.getUserId().toString()

val domainModule = module {

    factory<GetLocationUseCase> {
        GetLocationUseCase(iUserRepository = get())
    }

    factory<SaveLocationUseCase> {
        SaveLocationUseCase(
            iUserRepository = get(),
            userId = VK.getUserId().toString())
    }

    factory<GetPhotosUseCase> {
        GetPhotosUseCase(repository = get())
    }

    factory<GetFriendsListUseCase> {
        GetFriendsListUseCase(repository = get())
    }

    factory<ZoomUseCase> {
        ZoomUseCase(iMap = get())
    }

    factory<AddPlaceMarkUseCase> {
        AddPlaceMarkUseCase(iMap = get())
    }

    factory<MovePlaceMarkUseCase> {
        MovePlaceMarkUseCase(iMap = get())
    }

    factory<UpdateMapUseCase> {
        UpdateMapUseCase(iMap = get())
    }

    single<LocationListener>{
        YandexListener(
            userId = userId,
            updateMapUseCase = get(),
            saveLocationUseCase = get()
        )
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