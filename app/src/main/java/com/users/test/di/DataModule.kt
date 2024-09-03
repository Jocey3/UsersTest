package com.users.test.di

import android.content.Context
import androidx.room.Room
import com.users.test.data.mapper.UserMapper
import com.users.test.data.repository.RepositoryUsersImpl
import com.users.test.data.source.local.LocalDataSource
import com.users.test.data.source.local.LocalDataSourceImpl
import com.users.test.data.source.local.room.UsersDao
import com.users.test.data.source.local.room.UsersDb
import com.users.test.domain.repository.RepositoryUsers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DbName = "UsersTestDb"

val dataModule = module {
    single { provideRoomDatabase(androidContext()) }
    single { provideUsersDao(get()) }

    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
    single { UserMapper() }

    single<RepositoryUsers> { RepositoryUsersImpl(get(), get()) }
    single { UserMapper() }
}

fun provideRoomDatabase(context: Context): UsersDb {
    return Room.databaseBuilder(context, UsersDb::class.java, DbName).build()
}

private fun provideUsersDao(usersDb: UsersDb): UsersDao {
    return usersDb.usersDao()
}