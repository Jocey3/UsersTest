package com.users.test.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.users.test.data.source.local.room.entity.UserEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAll(): PagingSource<Int, UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Int): UserEntity

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)
}