package com.michel.vkmap.di

import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.api.VKApi
import com.michel.vkmap.data.db.FirebaseDataBase
import com.michel.vkmap.data.db.IDataBase
import com.michel.vkmap.data.listeners.YandexListener
import com.michel.vkmap.data.manager.YandexManager
import com.michel.vkmap.data.repository.IRepository
import com.michel.vkmap.data.repository.UserRepository
import com.michel.vkmap.data.sharedpref.SharedPrefStorage
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.GetFriendsListUseCase
import com.michel.vkmap.domain.usecases.GetFriendsLocationsUseCase
import com.michel.vkmap.domain.usecases.GetPhotoUseCase
import com.michel.vkmap.domain.usecases.GetUserLocationUseCase
import com.michel.vkmap.domain.usecases.SaveUserLocationUseCase
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.domain.usecases.ZoomUseCase
import com.michel.vkmap.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel>{
        MainViewModel(
            addPlaceMarkUseCase = get(),
            getFriendsListUseCase = get(),
            getFriendsLocationsUseCase = get(),
            getPhotoUseCase = get(),
            getUserLocationUseCase = get(),
            trackLocationUseCase = get(),
            zoomUseCase = get(),
            saveUserLocationUseCase = get()
        )
    }

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

    factory<GetPhotoUseCase> {
        GetPhotoUseCase(
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