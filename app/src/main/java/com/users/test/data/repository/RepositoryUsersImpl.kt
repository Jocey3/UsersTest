package com.users.test.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.users.test.data.mapper.UserMapper
import com.users.test.data.source.local.LocalDataSource
import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RepositoryUsersImpl(
    private val localDataSource: LocalDataSource,
    private val mapper: UserMapper
) : RepositoryUsers {
    override suspend fun addUser(user: UserDomainModel) {
        withContext(Dispatchers.IO) {
            delay(500) //Just for see progress bar
            localDataSource.insert(mapper.mapFromDomainToData(user))
        }
    }

    override fun getUsers(): Flow<PagingData<UserDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { localDataSource.getAll() }
        ).flow.map { pagingData ->
            pagingData.map { userDataModel ->
                mapper.mapFromDataToDomain(userDataModel)
            }
        }
    }

    override suspend fun getUserById(id: Int): UserDomainModel {
        return withContext(Dispatchers.IO) {
            delay(500)
            mapper.mapFromDataToDomain(localDataSource.getById(id))
        }
    }

    override suspend fun updateUser(user: UserDomainModel) {
        withContext(Dispatchers.IO) {
            delay(500)
            localDataSource.update(mapper.mapFromDomainToData(user))
        }
    }

    override suspend fun deleteUser(user: UserDomainModel) {
        withContext(Dispatchers.IO) {
            delay(500)
            localDataSource.delete(mapper.mapFromDomainToData(user))
        }
    }


}