package com.users.test.presentation.screens.add_user

import com.users.test.domain.use_case.AddUserUseCase
import com.users.test.presentation.mapper.UserMapper
import com.users.test.presentation.mvi_base.BaseViewModel
import com.users.test.presentation.screens.add_user.AddUserIntent.AddUser
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeDescription
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeName
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeUser
import com.users.test.presentation.screens.add_user.AddUserIntent.CompleteAddUser
import com.users.test.presentation.screens.add_user.AddUserIntent.DeleteUser
import com.users.test.presentation.screens.add_user.AddUserIntent.ValidateDescription
import com.users.test.presentation.screens.add_user.AddUserIntent.ValidateName

class AddUserViewModel(private val addUserUseCase: AddUserUseCase, private val mapper: UserMapper) :
    BaseViewModel<AddUserState, AddUserIntent, AddUserEvent>() {

    override val reducer: Reducer<AddUserState, AddUserIntent>
        get() = AddUserReducer()

    override fun initialState(): AddUserState {
        return AddUserState()
    }

    override suspend fun handleIntent(intent: AddUserIntent, state: AddUserState): AddUserIntent? {
        return when (intent) {
            is ChangeName -> {
                null
            }

            is ChangeDescription -> {
                null
            }

            is ValidateName -> {
                null
            }

            is ValidateDescription -> {
                null
            }

            is AddUser -> {
                try {
                    addUserUseCase(
                        mapper.mapFromUiToDomain(
                            UserUiModel(
                                name = state.user.name,
                                description = state.user.description
                            )
                        )
                    )
                    CompleteAddUser
                } catch (e: Exception) {
                    triggerSingleEvent(AddUserEvent.ShowToast("Something wrong when adding user"))
                    CompleteAddUser
                }
            }

            is CompleteAddUser -> {
                triggerSingleEvent(AddUserEvent.ShowToast("User added"))
                null
            }

            is ChangeUser -> {
                null
            }

            is DeleteUser -> {
                null
            }
        }
    }

    class AddUserReducer : Reducer<AddUserState, AddUserIntent> {
        override fun reduce(state: AddUserState, intent: AddUserIntent): AddUserState {
            return when (intent) {
                is ChangeName -> {
                    state.copy(user = state.user.copy(name = intent.name))
                }

                is ChangeDescription -> {
                    state.copy(user = state.user.copy(description = intent.description))
                }

                is ValidateName -> {
                    state.copy(
                        isNameError = state.user.name.isEmpty()
                    )
                }

                is ValidateDescription -> {
                    state.copy(
                        isDescriptionError = state.user.description.isEmpty()
                    )
                }

                is AddUser -> {
                    state.copy(isLoading = true)
                }

                is CompleteAddUser -> {
                    state.copy(
                        isLoading = false,
                        user = UserUiModel()
                    )
                }

                is ChangeUser -> {
                    AddUserState()
                }

                is DeleteUser -> {
                    AddUserState()
                }

            }
        }

    }

}
