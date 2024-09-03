package com.users.test.presentation.screens.list_users

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.users.test.presentation.util.EventConsumer
import org.koin.androidx.compose.getViewModel

@Composable
fun UserListScreen() {
    val context = LocalContext.current
    val viewModel: UserListViewModel = getViewModel()
    val state by viewModel.viewState.collectAsState()

    UserListContent()

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

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Users.....")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddUserPreview() {
    UserListContent()
}