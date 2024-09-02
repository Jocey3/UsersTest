package com.users.test

import android.app.Application
import com.users.test.di.dbModule
import com.users.test.di.localDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dbModule,
                localDataSourceModule
            )
        }
    }
}