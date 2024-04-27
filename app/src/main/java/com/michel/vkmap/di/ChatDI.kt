package com.michel.vkmap.di

import com.michel.vkmap.presentation.chat.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatDI = module {
    viewModel<UsersViewModel>{
        UsersViewModel(
            getFriendsListUseCase = get(),
            getUserInfoUseCase = get(),
            getNetworkStateUseCase = get()
        )
    }
}