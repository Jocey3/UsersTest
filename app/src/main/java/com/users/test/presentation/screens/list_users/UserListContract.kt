package com.users.test.presentation.screens.list_users

import androidx.paging.PagingData
import com.users.test.presentation.mvi_base.Intent
import com.users.test.presentation.mvi_base.SingleEvent
import com.users.test.presentation.mvi_base.State
import com.users.test.presentation.screens.add_user.UserUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class UserListState(
    val isLoading: Boolean = false,
    val userListFlow: Flow<PagingData<UserUiModel>> = emptyFlow()
) : State

sealed class UserListIntent : Intent {
    data object LoadUsers : UserListIntent()
    data class ShowUsers(val userList: Flow<PagingData<UserUiModel>>) : UserListIntent()
}

sealed class UserListEvent : SingleEvent {
    data class ShowToast(val message: String) : UserListEvent()
}