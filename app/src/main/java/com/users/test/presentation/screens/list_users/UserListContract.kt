package com.users.test.presentation.screens.list_users

import com.users.test.presentation.mvi_base.Intent
import com.users.test.presentation.mvi_base.SingleEvent
import com.users.test.presentation.mvi_base.State
import com.users.test.presentation.screens.add_user.UserUiModel

data class UserListState(
    val isLoading: Boolean = false,
    val userList: List<UserUiModel> = listOf()
) : State

sealed class UserListIntent : Intent {
    data object LoadUsers : UserListIntent()
    data class ShowUsers(val userList: List<UserUiModel>) : UserListIntent()
}

sealed class UserListEvent : SingleEvent {
    data class ShowToast(val message: String) : UserListEvent()
}