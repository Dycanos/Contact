package com.example.contact.ui.theme.person

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contact.ContactTopAppBar
import com.example.contact.R
import com.example.contact.data.Person
import com.example.contact.ui.theme.AppViewModelProvider
import com.example.contact.ui.theme.home.PersonEmail
import com.example.contact.ui.theme.home.PersonPhoneNumber
import com.example.contact.ui.theme.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ContactDetailsDestination: NavigationDestination{
    override val route = "person_details"
    override val titleRes = R.string.title_res
    const val personIdArg = "personId"
    val routeWithArgs = "${route}/{$personIdArg}"

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    navigateToUpdatePerson:(Int)->Unit,
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ContactTopAppBar(
                title = stringResource(ContactDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToUpdatePerson(uiState.value.personDetail.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_item_title),
                )
            }
        }, modifier = modifier
    ) {innerPadding->
        PersonDetailsBody(
            personDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deletePerson()
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
fun PersonDetailsBody(
    personDetailsUiState: PersonDetailsUiState,
    onDelete: ()-> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        PersonDetails(
            person = personDetailsUiState.personDetail.toItem(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete), fontSize = 20.sp)
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun PersonDetails(
    person: Person,
    modifier: Modifier = Modifier){

    Card(modifier = modifier,
        border = BorderStroke(2.dp,MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = person.lastName,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
            Text(text = person.firstName, fontSize =30.sp)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
            Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 30.dp))
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
            PersonPhoneNumber(person = person,fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
            PersonEmail(person = person,fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
        }
    }

}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}


@Preview
@Composable
fun PersonDetailPreview(){
    PersonDetails(person = Person(0,"Louis","Jacques","0616157153","louis.jacques@soprasteria.com"))
}