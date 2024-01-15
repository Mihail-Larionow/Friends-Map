package com.michel.vkmap.di

import com.michel.vkmap.R
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.presentation.MainViewModel
import com.michel.vkmap.presentation.map.YandexMap
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel>{
        MainViewModel(
            getLocationUseCase = get(),
            saveLocationUseCase = get(),
            trackLocationUseCase = get(),
            getPhotosUseCase = get(),
            zoomUseCase = get(),
            displayMapUseCase = get(),
            addPlaceMarkUseCase = get()
        )
    }

    single<IMap>{
        YandexMap()
    }
}