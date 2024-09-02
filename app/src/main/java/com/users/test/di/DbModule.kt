package com.users.test.di

import android.content.Context
import androidx.room.Room
import com.users.test.data.source.local.UsersDao
import com.users.test.data.source.local.UsersDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DbName = "UsersTestDb"

val dbModule = module {
    single { provideRoomDatabase(androidContext()) }
    single { provideUsersDao(get()) }
}

fun provideRoomDatabase(context: Context): UsersDb {
    return Room.databaseBuilder(context, UsersDb::class.java, DbName).build()
}

private fun provideUsersDao(usersDb: UsersDb): UsersDao {
    return usersDb.usersDao()
}