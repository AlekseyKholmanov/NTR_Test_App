package com.example.ntr_test_app.di

import com.example.ntr_test_app.useCases.FetchStateDetailsUseCase
import com.example.ntr_test_app.useCases.FetchStateDetailsUseCaseImpl
import com.example.ntr_test_app.useCases.FetchStatesUseCase
import com.example.ntr_test_app.useCases.FetchStatesUseCaseImpl
import org.koin.dsl.module

val useCasesModule = module {
    single<FetchStatesUseCase> {
        FetchStatesUseCaseImpl(api = get())
    }

    single<FetchStateDetailsUseCase> {
        FetchStateDetailsUseCaseImpl(api = get())
    }
}