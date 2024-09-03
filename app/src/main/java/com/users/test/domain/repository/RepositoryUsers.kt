package com.users.test.domain.repository

import com.users.test.domain.model.UserDomainModel

interface RepositoryUsers {
    suspend fun addUser(user: UserDomainModel)
}