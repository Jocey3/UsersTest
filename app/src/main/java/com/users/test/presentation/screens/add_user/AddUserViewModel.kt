package com.users.test.presentation.screens.add_user

import com.users.test.domain.use_case.AddUserUseCase
import com.users.test.domain.use_case.GetUserByIdUseCase
import com.users.test.domain.use_case.UpdateUserUseCase
import com.users.test.presentation.mapper.UserMapper
import com.users.test.presentation.mvi_base.BaseViewModel
import com.users.test.presentation.screens.add_user.AddUserIntent.AddUser
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeDescription
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeName
import com.users.test.presentation.screens.add_user.AddUserIntent.CompleteUser
import com.users.test.presentation.screens.add_user.AddUserIntent.GetUser
import com.users.test.presentation.screens.add_user.AddUserIntent.ShowUser
import com.users.test.presentation.screens.add_user.AddUserIntent.UpdateUser
import com.users.test.presentation.screens.add_user.AddUserIntent.ValidateDescription
import com.users.test.presentation.screens.add_user.AddUserIntent.ValidateName

class AddUserViewModel(
    private val userIdArg: String,
    private val addUserUseCase: AddUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val mapper: UserMapper
) : BaseViewModel<AddUserState, AddUserIntent, AddUserEvent>() {

    override val reducer: Reducer<AddUserState, AddUserIntent>
        get() = AddUserReducer()

    override fun initialState(): AddUserState {
        try {
            val userid = userIdArg.toInt()
            sendIntent(GetUser(userId = userid))
            return AddUserState(isLoading = true, isUpdating = true)
        } catch (_: Exception) {
            return AddUserState()
        }
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

                if (state.user.name.isNotEmpty() and state.user.description.isNotEmpty()) {
                    try {
                        addUserUseCase(
                            mapper.mapFromUiToDomain(
                                UserUiModel(
                                    name = state.user.name,
                                    description = state.user.description
                                )
                            )
                        )
                        triggerSingleEvent(AddUserEvent.ShowToast("User added"))
                        CompleteUser
                    } catch (e: Exception) {
                        triggerSingleEvent(AddUserEvent.ShowToast("Something wrong when adding user"))
                        null
                    }
                } else {
                    triggerSingleEvent(AddUserEvent.ShowToast("Please input name and description"))
                    null
                }

            }

            is GetUser -> {
                try {
                    ShowUser(mapper.mapFromDomainToUi(getUserByIdUseCase(intent.userId)))
                } catch (e: Exception) {
                    triggerSingleEvent(AddUserEvent.ShowToast("Something wrong when getting user"))
                    CompleteUser
                }
            }

            is UpdateUser -> {
                try {
                    updateUserUseCase(mapper.mapFromUiToDomain(state.user))
                    triggerSingleEvent(AddUserEvent.ShowToast("Done"))
                    CompleteUser
                } catch (e: Exception) {
                    triggerSingleEvent(AddUserEvent.ShowToast("Something wrong when updating user"))
                    null
                }
            }

            else -> {
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
                    state.copy(isNameError = state.user.name.isEmpty())
                }

                is ValidateDescription -> {
                    state.copy(isDescriptionError = state.user.description.isEmpty())
                }

                is AddUser -> {
                    state.copy(isLoading = (state.user.name.isNotEmpty() and state.user.description.isNotEmpty()))
                }

                is GetUser, is UpdateUser -> {
                    state.copy(isLoading = true)
                }

                is ShowUser -> {
                    state.copy(isLoading = false, user = intent.user)
                }

                CompleteUser -> {
                    AddUserState()
                }

            }
        }

    }

}
