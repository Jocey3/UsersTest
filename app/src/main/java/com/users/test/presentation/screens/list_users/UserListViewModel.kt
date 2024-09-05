package com.users.test.presentation.screens.list_users

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.users.test.domain.use_case.GetUsersUseCase
import com.users.test.presentation.mapper.UserMapper
import com.users.test.presentation.mvi_base.BaseViewModel
import com.users.test.presentation.screens.list_users.UserListIntent.LoadUsers
import com.users.test.presentation.screens.list_users.UserListIntent.ShowUsers
import kotlinx.coroutines.flow.map

class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val mapper: UserMapper
) : BaseViewModel<UserListState, UserListIntent, UserListEvent>() {

    init {
        sendIntent(LoadUsers)
    }

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
                val userListFlow = getUsersUseCase()
                    .map { pagingData ->
                        pagingData.map { userDomainModel ->
                            Log.d("myLog", userDomainModel.toString())
                            mapper.mapFromDomainToUi(userDomainModel)
                        }
                    }
                    .cachedIn(viewModelScope)
                sendIntent(ShowUsers(userListFlow))
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
                is LoadUsers -> state.copy(isLoading = true)
                is ShowUsers -> state.copy(isLoading = false, userListFlow = intent.userList)

            }
        }

    }

}