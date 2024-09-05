package com.users.test.domain.use_case

import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers


class UpdateUserUseCase(private val repositoryUsers: RepositoryUsers) {
    suspend operator fun invoke(user: UserDomainModel) {
        if (user.name.isEmpty() and user.description.isEmpty()) repositoryUsers.deleteUser(user)
        else repositoryUsers.updateUser(user)
    }
}