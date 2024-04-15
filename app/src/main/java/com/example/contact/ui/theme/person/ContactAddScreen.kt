package com.example.contact.ui.theme.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contact.ContactTopAppBar
import com.example.contact.R
import com.example.contact.ui.theme.AppViewModelProvider
import com.example.contact.ui.theme.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ContactAddDestination : NavigationDestination {
    override val route = "add"
    override val titleRes = R.string.add_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactAddScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: ContactAddViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ContactTopAppBar(
                title = stringResource(ContactAddDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) {innerPadding->
        PersonAddBody(
            personUiState = viewModel.personUiState,
            onPersonValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.savePerson()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun PersonAddBody(
    personUiState: PersonUiState,
    onPersonValueChange: (PersonDetails)->Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier) {
        PersonAddForm(
            personDetails = personUiState.personDetails,
            onValueChange = onPersonValueChange,
            modifier = Modifier)
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_extraSmall)))
        Button(onClick = onSaveClick) {
            Text(text = stringResource(id = R.string.save), fontSize = 20.sp)
        }
    }
}

@Composable
fun PersonAddForm(
    personDetails: PersonDetails,
    onValueChange: (PersonDetails) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = personDetails.firstName,
            onValueChange = {
            onValueChange(
                personDetails.copy(firstName = it)
            )},
            label ={ Text(text = stringResource(R.string.firstName))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_medium), end = dimensionResource(id = R.dimen.padding_medium)),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = personDetails.lastName,
            onValueChange = {
            onValueChange(
                personDetails.copy(lastName = it)
            )},
            label ={ Text(text = stringResource(R.string.lastName))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_medium), end = dimensionResource(id = R.dimen.padding_medium)),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = personDetails.phoneNumber,
            onValueChange = {
                onValueChange(
                    personDetails.copy(phoneNumber = it)
                )},
            label ={ Text(text = stringResource(R.string.phoneNumber))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_medium), end = dimensionResource(id = R.dimen.padding_medium)),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = personDetails.email,
            onValueChange = {
                onValueChange(
                    personDetails.copy(email = it)
                )},
            label ={ Text(text = stringResource(R.string.email))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_medium), end = dimensionResource(id = R.dimen.padding_medium)),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Preview
@Composable
fun PersonAddBodyPreview(){
    PersonAddBody(personUiState = PersonUiState(
        PersonDetails(id = 0, firstName = "Louis", lastName = "JACQUES", phoneNumber = "0616157153", email = "louis.jacques@soprasteria.com")
    ) , onPersonValueChange = {}, onSaveClick = { }, modifier = Modifier.fillMaxWidth())
}