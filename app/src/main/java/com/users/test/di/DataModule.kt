package com.users.test.di

import android.content.Context
import androidx.room.Room
import com.users.test.data.mapper.UserMapper
import com.users.test.data.source.local.LocalDataSource
import com.users.test.data.source.local.LocalDataSourceImpl
import com.users.test.data.source.local.room.UsersDao
import com.users.test.data.source.local.room.UsersDb
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

val localDataSourceModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
    single { UserMapper() }
}
