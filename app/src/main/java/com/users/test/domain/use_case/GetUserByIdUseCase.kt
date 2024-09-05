package com.users.test.domain.use_case

import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers

class GetUserByIdUseCase(private val repositoryUsers: RepositoryUsers) {
    suspend operator fun invoke(userId: Int): UserDomainModel {
        return repositoryUsers.getUserById(userId)
    }

}