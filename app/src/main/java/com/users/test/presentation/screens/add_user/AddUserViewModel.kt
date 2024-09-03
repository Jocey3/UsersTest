package com.users.test.presentation.screens.add_user

import com.users.test.domain.use_case.AddUserUseCase
import com.users.test.presentation.mapper.UserMapper
import com.users.test.presentation.mvi_base.BaseViewModel
import com.users.test.presentation.screens.add_user.AddUserIntent.AddUser
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeUser
import com.users.test.presentation.screens.add_user.AddUserIntent.DeleteUser

class AddUserViewModel(private val addUserUseCase: AddUserUseCase, private val mapper: UserMapper) :
    BaseViewModel<AddUserState, AddUserIntent, AddUserEvent>() {

    override val reducer: Reducer<AddUserState, AddUserIntent>
        get() = AddUserReducer()

    override fun initialState(): AddUserState {
        return AddUserState()
    }

    override suspend fun handleIntent(intent: AddUserIntent, state: AddUserState): AddUserIntent? {
        return when (intent) {
            is AddUser -> {
                if (intent.name.isNotEmpty() and intent.description.isNotEmpty()) {
                    try {
                        addUserUseCase(
                            mapper.mapFromUiToDomain(
                                UserUiModel(
                                    name = intent.name,
                                    description = intent.description
                                )
                            )
                        )
                        triggerSingleEvent(AddUserEvent.ShowToast("User added"))
                    } catch (e: Exception) {
                        triggerSingleEvent(AddUserEvent.ShowToast("Something wrong when adding user"))
                    }
                } else {
                    triggerSingleEvent(AddUserEvent.ShowToast("Please input name and description"))
                }
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
                is AddUser -> {
                    AddUserState(!state.isInProcess)
                }

                is ChangeUser -> {
                    AddUserState(!state.isInProcess)
                }

                is DeleteUser -> {
                    AddUserState(!state.isInProcess)
                }

            }
        }

    }

}
