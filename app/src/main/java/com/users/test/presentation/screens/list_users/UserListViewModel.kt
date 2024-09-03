package com.users.test.presentation.screens.list_users

import com.users.test.domain.use_case.AddUserUseCase
import com.users.test.presentation.mapper.UserMapper
import com.users.test.presentation.mvi_base.BaseViewModel
import com.users.test.presentation.screens.list_users.UserListIntent.LoadUsers
import com.users.test.presentation.screens.list_users.UserListIntent.ShowUsers

class UserListViewModel(
    private val addUserUseCase: AddUserUseCase,
    private val mapper: UserMapper
) : BaseViewModel<UserListState, UserListIntent, UserListEvent>() {
    override val reducer: Reducer<UserListState, UserListIntent>
        get() = UserListReducer()

    override fun initialState(): UserListState {
        return UserListState()
    }

    override suspend fun handleIntent(
        intent: UserListIntent,
        state: UserListState
    ): UserListIntent? {
        return when (intent) {
            is LoadUsers -> {
                null
            }

            is ShowUsers -> {
                null
            }
        }
    }

    class UserListReducer : Reducer<UserListState, UserListIntent> {
        override fun reduce(state: UserListState, intent: UserListIntent): UserListState {
            return when (intent) {
                is LoadUsers -> {
                    state
                }

                is ShowUsers -> {
                    state
                }
            }
        }

    }

}