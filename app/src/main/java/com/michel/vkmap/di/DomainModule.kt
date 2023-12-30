package com.michel.vkmap.di

import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetLocationUseCase> {
        GetLocationUseCase(iUserRepository = get())
    }

    factory<SaveLocationUseCase> {
        SaveLocationUseCase(iUserRepository = get())
    }

}