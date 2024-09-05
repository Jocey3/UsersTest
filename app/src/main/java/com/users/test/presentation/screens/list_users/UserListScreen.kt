package com.users.test.presentation.screens.list_users

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.users.test.presentation.screens.add_user.UserUiModel
import com.users.test.presentation.util.EventConsumer
import org.koin.androidx.compose.getViewModel

@Composable
fun UserListScreen(navigateToUser: (userId: String) -> Unit = {}) {
    val context = LocalContext.current
    val viewModel: UserListViewModel = getViewModel()
    val state by viewModel.viewState.collectAsState()
    val lazyPagingItems = state.userListFlow.collectAsLazyPagingItems()


    UserListContent(lazyPagingItems,
        onItemClick = { userId ->
            navigateToUser(userId)
        })

    EventConsumer(viewModel.eventChannel) {
        when (it) {
            is UserListEvent.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}

@Composable
fun UserListContent(
    lazyPagingItems: LazyPagingItems<UserUiModel>,
    onItemClick: (userId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            item?.let {
                UserListItem(
                    user = it,
                    onClick = { item.id?.let { userId -> onItemClick(userId.toString()) } })
            }

        }

        when {
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            lazyPagingItems.loadState.append is LoadState.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            lazyPagingItems.loadState.append is LoadState.Error -> {
                item {
                    Text(
                        text = "Error loading more items",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun UserListItem(user: UserUiModel, onClick: () -> Unit) {
    Text(
        text = user.name,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddUserPreview() {

}