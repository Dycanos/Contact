package com.example.contact.ui.theme.person

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contact.ContactTopAppBar
import com.example.contact.R
import com.example.contact.ui.theme.AppViewModelProvider
import com.example.contact.ui.theme.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ContactUpdateDestination : NavigationDestination {
    override val route = "person_update"
    override val titleRes = R.string.title_res
    const val personIdArg = "personId"
    val routeWithArgs = "$route/{$personIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUpdateScreen(
    navigateBack: ()-> Unit,
    onNavigateUp: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ContactTopAppBar(
                title = stringResource(ContactUpdateDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        PersonAddBody(
            personUiState = viewModel.personUiState,
            onPersonValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePerson()
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

@Preview
@Composable
fun ContactUpdateScreenPreview(){

}