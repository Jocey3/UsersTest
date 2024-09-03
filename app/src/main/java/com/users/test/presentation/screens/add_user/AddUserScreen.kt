package com.users.test.presentation.screens.add_user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.users.test.R
import com.users.test.presentation.screens.add_user.AddUserEvent.ShowToast
import com.users.test.presentation.screens.add_user.AddUserIntent.AddUser
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeDescription
import com.users.test.presentation.screens.add_user.AddUserIntent.ChangeName
import com.users.test.presentation.screens.add_user.AddUserIntent.ValidateDescription
import com.users.test.presentation.screens.add_user.AddUserIntent.ValidateName
import com.users.test.presentation.util.EventConsumer
import org.koin.androidx.compose.getViewModel

@Composable
fun AddUserScreen() {
    val context = LocalContext.current
    val viewModel: AddUserViewModel = getViewModel()
    val state by viewModel.viewState.collectAsState()

    AddUserContent(
        state,
        changeName = { viewModel.sendIntent(ChangeName(it)) },
        changeDescription = { viewModel.sendIntent(ChangeDescription(it)) },
        validateName = { viewModel.sendIntent(ValidateName) },
        validateDescription = { viewModel.sendIntent(ValidateDescription) },
        clickAdd = {
            if (state.user.name.isNotEmpty() and state.user.description.isNotEmpty()) {
                viewModel.sendIntent(AddUser)
            } else {
                viewModel.triggerSingleEvent(ShowToast("Please input name and description"))
            }

        })

    EventConsumer(viewModel.eventChannel) {
        when (it) {
            is ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}

@Composable
fun AddUserContent(
    state: AddUserState,
    changeName: (name: String) -> Unit,
    changeDescription: (description: String) -> Unit,
    validateName: () -> Unit,
    validateDescription: () -> Unit,
    clickAdd: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isNameError) {
            Text(
                text = stringResource(R.string.error_name_required),
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        TextField(
            enabled = !state.isLoading,
            value = state.user.name,
            onValueChange = {
                changeName(it)
                validateName()
            },
            label = { Text(stringResource(R.string.name)) },
            isError = state.isNameError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (state.isDescriptionError) {
            Text(
                text = stringResource(R.string.error_description_required),
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        TextField(
            enabled = !state.isLoading,
            value = state.user.description,
            onValueChange = {
                changeDescription(it)
                validateDescription()
            },
            label = { Text(stringResource(R.string.description)) },
            isError = state.isDescriptionError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Button(
                onClick = {
                    validateName()
                    validateDescription()
                    clickAdd()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.add))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddUserPreview() {
    AddUserContent(
        AddUserState(isLoading = true),
        changeName = { },
        changeDescription = { },
        validateName = { },
        validateDescription = { },
        clickAdd = { })
}