package com.users.test.data.source.local

import androidx.paging.PagingSource
import com.users.test.data.model.UserDataModel


//This type use for divide room db from repository. So our repository don`t know what we use for saving data.
//In future if we decide use other library for db this abstraction will help us... In our repository we also have specific model, _DataModel.
interface LocalDataSource {
    fun getAll(): PagingSource<Int, UserDataModel>
    suspend fun insert(user: UserDataModel)
    suspend fun getById(id: Int): UserDataModel
    suspend fun insertAll(users: List<UserDataModel>)
    suspend fun update(user: UserDataModel)
    suspend fun delete(user: UserDataModel)
}