package com.users.test.di

import com.users.test.presentation.mapper.UserMapper
import com.users.test.presentation.screens.add_user.AddUserViewModel
import com.users.test.presentation.screens.list_users.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { AddUserViewModel(get(), get()) }
    viewModel { UserListViewModel(get(), get()) }
    single { UserMapper() }
}