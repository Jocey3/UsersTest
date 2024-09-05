package com.users.test.domain.use_case

import androidx.paging.PagingData
import com.users.test.domain.model.UserDomainModel
import com.users.test.domain.repository.RepositoryUsers
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(private val repositoryUsers: RepositoryUsers) {
    operator fun invoke(): Flow<PagingData<UserDomainModel>> {
        return repositoryUsers.getUsers()
    }
}