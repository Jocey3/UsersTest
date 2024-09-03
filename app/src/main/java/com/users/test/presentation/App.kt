package com.users.test.presentation

import android.app.Application

import com.users.test.di.dataModule
import com.users.test.di.domainModule
import com.users.test.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}