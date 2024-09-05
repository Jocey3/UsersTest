package com.users.test.presentation.screens.list_users

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextOverflow
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val wordCount = user.description.split("\\s+".toRegex()).size

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Name:",
                color = Color.Gray
            )
            Text(
                text = user.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Description:",
                color = Color.Gray
            )
            Text(
                text = user.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Display the word count of the description
            Text(
                text = "Word Count: $wordCount words",
                color = Color.Gray,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddUserPreview() {

}