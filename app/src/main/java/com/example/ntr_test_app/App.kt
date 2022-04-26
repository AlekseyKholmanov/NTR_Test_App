package com.example.ntr_test_app

import android.app.Application
import com.example.ntr_test_app.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        buildKoin()
    }

    private fun buildKoin(){
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }
}