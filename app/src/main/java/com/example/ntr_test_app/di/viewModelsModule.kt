package com.example.ntr_test_app.di

import com.example.ntr_test_app.ui.StateDetailsViewModel
import com.example.ntr_test_app.ui.StateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        StateViewModel(
            fetchStatesUseCase = get()
        )
    }

    viewModel {
        StateDetailsViewModel(
            fetchStateDetailsUseCase = get()
        )
    }


}