package com.michel.vkmap.di

import com.michel.vkmap.data.repository.UserRepository
import com.michel.vkmap.data.storage.IUserStorage
import com.michel.vkmap.data.storage.sharedpref.SharedPrefUserStorage
import com.michel.vkmap.domain.repository.IUserRepository
import org.koin.dsl.module

val dataModule = module {

    single<IUserStorage> {
        SharedPrefUserStorage(context = get())
    }

    single<IUserRepository> {
        UserRepository(iUserStorage = get())
    }

}