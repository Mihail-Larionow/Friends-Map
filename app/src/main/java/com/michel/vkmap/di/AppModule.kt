package com.michel.vkmap.di

import com.michel.vkmap.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel>(

    )
}