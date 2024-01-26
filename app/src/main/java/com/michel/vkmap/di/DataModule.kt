package com.michel.vkmap.di

import com.google.firebase.database.ValueEventListener
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.api.vk.VKApi
import com.michel.vkmap.data.database.IDataBase
import com.michel.vkmap.data.database.firebase.FireBaseDataBase
import com.michel.vkmap.data.database.firebase.FireBaseListener
import com.michel.vkmap.data.repository.UserRepository
import com.michel.vkmap.data.repository.VKRepository
import com.michel.vkmap.data.storage.IUserStorage
import com.michel.vkmap.data.storage.sharedpref.SharedPrefUserStorage
import com.michel.vkmap.domain.repository.IUserRepository
import com.michel.vkmap.domain.repository.IVKRepository
import org.koin.dsl.module

val dataModule = module {

    single<IUserStorage> {
        SharedPrefUserStorage(context = get())
    }

    single<ValueEventListener>{
        FireBaseListener(updateLocationUseCase = get())
    }

    single<IDataBase>{
        FireBaseDataBase(dataListener = get())
    }

    single<IApi>{
        VKApi()
    }

    single<IUserRepository> {
        UserRepository(
            iUserStorage = get(),
            iDataBase = get(),
        )
    }

    single<IVKRepository> {
        VKRepository(
            iApi = get()
        )
    }

}