package com.users.test.di

import com.users.test.domain.use_case.AddUserUseCase
import com.users.test.domain.use_case.GetUserByIdUseCase
import com.users.test.domain.use_case.GetUsersUseCase
import com.users.test.domain.use_case.UpdateUserUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { AddUserUseCase(get()) }
    factory { GetUsersUseCase(get()) }
    factory { GetUserByIdUseCase(get()) }
    factory { UpdateUserUseCase(get()) }
}