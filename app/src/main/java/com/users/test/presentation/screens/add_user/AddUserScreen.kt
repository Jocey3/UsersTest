package com.users.test.presentation.screens.add_user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.users.test.presentation.screens.add_user.AddUserEvent.ShowToast
import com.users.test.presentation.util.EventConsumer
import org.koin.androidx.compose.getViewModel

@Composable
fun AddUserScreen() {
    val context = LocalContext.current
    val viewModel: AddUserViewModel = getViewModel()
    val state by viewModel.viewState.collectAsState()

    AddUserContent(state) { name, description ->
        viewModel.sendIntent(AddUserIntent.AddUser(name, description))
    }

    EventConsumer(viewModel.eventChannel) {
        when (it) {
            is ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}

@Composable
fun AddUserContent(state: AddUserState, addUserClick: (name: String, description: String) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            enabled = !state.isInProcess,
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            enabled = !state.isInProcess,
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        if (state.isInProcess) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
        } else {
            Button(
                onClick = { addUserClick(name, description) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Add")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddUserPreview() {
    AddUserContent(AddUserState(isInProcess = false)) { _, _ -> }
}