package com.users.test.presentation.screens.add_user

import com.users.test.presentation.mvi_base.Intent
import com.users.test.presentation.mvi_base.SingleEvent
import com.users.test.presentation.mvi_base.State

data class AddUserState(
    val isInProcess: Boolean = false
) : State

sealed class AddUserIntent : Intent {
    data class AddUser(val name: String, val description: String) : AddUserIntent()
    data class ChangeUser(val user: UserUiModel) : AddUserIntent()
    data class DeleteUser(val user: UserUiModel) : AddUserIntent()
}

sealed class AddUserEvent : SingleEvent {
    data class ShowToast(val message: String) : AddUserEvent()
}

data class UserUiModel(val id: Int? = null, val name: String, val description: String)