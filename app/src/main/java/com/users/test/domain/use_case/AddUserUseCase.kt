package com.users.test.domain.use_case

import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers

class AddUserUseCase(private val repositoryUsers: RepositoryUsers) {
    suspend operator fun invoke(number: UserDomainModel) {
        return repositoryUsers.addUser(number)
    }
}