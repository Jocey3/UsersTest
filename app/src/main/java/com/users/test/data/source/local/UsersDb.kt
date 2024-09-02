package com.users.test.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.users.test.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsersDb : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}