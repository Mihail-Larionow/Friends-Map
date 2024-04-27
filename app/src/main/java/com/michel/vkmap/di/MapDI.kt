package com.michel.vkmap.di

import com.michel.vkmap.presentation.chat.UsersViewModel
import com.michel.vkmap.presentation.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapDI = module {
    viewModel<MapViewModel>{
        MapViewModel(
            addPlaceMarkUseCase = get(),
            getFriendsListUseCase = get(),
            getFriendsLocationsUseCase = get(),
            getUserInfoUseCase = get(),
            getUserLocationUseCase = get(),
            trackLocationUseCase = get(),
            zoomUseCase = get(),
            getNetworkStateUseCase = get(),
            saveUserLocationUseCase = get()
        )
    }
}