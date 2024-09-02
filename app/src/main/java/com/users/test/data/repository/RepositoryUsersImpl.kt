package com.users.test.data.repository

import com.users.test.data.source.local.LocalDataSource
import com.users.test.domain.repository.RepositoryUsers

class RepositoryUsersImpl(private val localDataSource: LocalDataSource) : RepositoryUsers {


}