package com.michel.vkmap.di

import com.google.firebase.database.ValueEventListener
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.api.vk.VKApi
import com.michel.vkmap.data.database.IDataBase
import com.michel.vkmap.data.database.firebase.FireBaseDataBase
import com.michel.vkmap.data.database.firebase.FireBaseListener
import com.michel.vkmap.data.repository.UserRepository
import com.michel.vkmap.data.storage.IUserStorage
import com.michel.vkmap.data.storage.sharedpref.SharedPrefUserStorage
import com.michel.vkmap.domain.repository.IUserRepository
import org.koin.dsl.module

val dataModule = module {

    single<IUserStorage> {
        SharedPrefUserStorage(context = get())
    }

    single<ValueEventListener>{
        FireBaseListener(movePlaceMarkUseCase = get())
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
            iApi = get()
        )
    }

}