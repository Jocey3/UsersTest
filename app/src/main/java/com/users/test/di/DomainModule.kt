package com.users.test.di

import com.users.test.domain.use_case.AddUserUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { AddUserUseCase(get()) }
}