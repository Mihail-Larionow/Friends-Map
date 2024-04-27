package com.michel.vkmap.di

import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.api.VKApi
import com.michel.vkmap.data.db.FirebaseDataBase
import com.michel.vkmap.data.db.IDataBase
import com.michel.vkmap.data.listeners.YandexListener
import com.michel.vkmap.data.manager.YandexManager
import com.michel.vkmap.domain.repository.IRepository
import com.michel.vkmap.data.repository.UserRepository
import com.michel.vkmap.data.sharedpref.SharedPrefStorage
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.GetFriendsListUseCase
import com.michel.vkmap.domain.usecases.GetFriendsLocationsUseCase
import com.michel.vkmap.domain.usecases.GetUserInfoUseCase
import com.michel.vkmap.domain.usecases.GetNetworkStateUseCase
import com.michel.vkmap.domain.usecases.GetUserLocationUseCase
import com.michel.vkmap.domain.usecases.SaveUserLocationUseCase
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.domain.usecases.ZoomUseCase
import org.koin.dsl.module

val appModule = module {

    factory<AddPlaceMarkUseCase>{
        AddPlaceMarkUseCase()
    }

    factory<GetFriendsListUseCase>{
        GetFriendsListUseCase(
            repository = get()
        )
    }

    factory<GetFriendsLocationsUseCase>{
        GetFriendsLocationsUseCase(
            repository = get()
        )
    }

    factory<GetNetworkStateUseCase> {
        GetNetworkStateUseCase(
            repository = get()
        )
    }

    factory<GetUserInfoUseCase> {
        GetUserInfoUseCase(
            repository = get()
        )
    }

    factory<GetUserLocationUseCase> {
        GetUserLocationUseCase(
            manager = get()
        )
    }

    single<TrackLocationUseCase>{
        TrackLocationUseCase(
            manager = get()
        )
    }

    factory<ZoomUseCase>{
        ZoomUseCase()
    }

    factory<SaveUserLocationUseCase>{
        SaveUserLocationUseCase(
            repository = get()
        )
    }

    single<IRepository>{
        UserRepository(
            api = get(),
            dataBase = get(),
            sharedPref = get()
        )
    }

    single<IDataBase>{
        FirebaseDataBase()
    }


    single<IApi>{
        VKApi()
    }

    single<SharedPrefStorage>{
        SharedPrefStorage(
            context = get()
        )
    }

    single<YandexManager>{
        YandexManager(
            locationListener = get()
        )
    }

    single<YandexListener>{
        YandexListener()
    }

}