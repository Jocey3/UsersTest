package com.users.test.presentation.screens.add_user

import androidx.compose.runtime.Immutable
import com.users.test.presentation.mvi_base.Intent
import com.users.test.presentation.mvi_base.SingleEvent
import com.users.test.presentation.mvi_base.State

data class AddUserState(
    val isLoading: Boolean = false,
    val user: UserUiModel = UserUiModel(),
    val isNameError: Boolean = false,
    val isDescriptionError: Boolean = false,
) : State

sealed class AddUserIntent : Intent {
    data class ChangeName(val name: String) : AddUserIntent()
    data class ChangeDescription(val description: String) : AddUserIntent()
    data object ValidateName : AddUserIntent()
    data object ValidateDescription : AddUserIntent()
    data object AddUser : AddUserIntent()
    data class ChangeUser(val user: UserUiModel) : AddUserIntent()
    data class DeleteUser(val user: UserUiModel) : AddUserIntent()
    data object CompleteAddUser : AddUserIntent()
}

sealed class AddUserEvent : SingleEvent {
    data class ShowToast(val message: String) : AddUserEvent()
}

@Immutable
data class UserUiModel(val id: Int? = null, val name: String = "", val description: String = "")