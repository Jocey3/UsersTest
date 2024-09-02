package com.users.test.data.source.local

import com.users.test.data.mapper.UserMapper
import com.users.test.data.model.UserDataModel
import com.users.test.data.source.local.room.UsersDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val usersDao: UsersDao,
    private val mapper: UserMapper
) :
    LocalDataSource {
    override fun getAll(): Flow<List<UserDataModel>> {
        return usersDao.getAll().map {
            it.map { entity ->
                mapper.mapFromEntityToData(entity)
            }
        }
    }

    override suspend fun insert(user: UserDataModel) {
        usersDao.insert(mapper.mapFromDataToEntity(user))
    }

    override suspend fun insertAll(users: List<UserDataModel>) {
        usersDao.insertAll(users.map { user -> mapper.mapFromDataToEntity(user) })
    }

    override suspend fun update(user: UserDataModel) {
        usersDao.update(mapper.mapFromDataToEntity(user))
    }

    override suspend fun delete(user: UserDataModel) {
        usersDao.delete(mapper.mapFromDataToEntity(user))
    }
}