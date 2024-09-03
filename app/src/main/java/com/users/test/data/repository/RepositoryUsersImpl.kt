package com.users.test.data.repository

import com.users.test.data.mapper.UserMapper
import com.users.test.data.source.local.LocalDataSource
import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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


}