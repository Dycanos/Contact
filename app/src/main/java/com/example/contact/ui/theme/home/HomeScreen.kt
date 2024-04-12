package com.example.contact.ui.theme.home

import android.content.ClipData.Item
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contact.ContactTopAppBar
import com.example.contact.R
import com.example.contact.data.Person
import com.example.contact.ui.theme.AppViewModelProvider
import com.example.contact.ui.theme.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToPersonEntry: ()->Unit,
    navigateToPersonUpdate: (Int)->Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val homeUiState by viewModel.homeUiState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ContactTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }
    ) {innerPadding ->
        HomeBody(
            personList = homeUiState.personList,
            onPersonClick = navigateToPersonUpdate,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }

}

@Composable
private fun HomeBody(
    personList:List<Person>,
    onPersonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (personList.isEmpty()){
                Text(text = stringResource(R.string.no_contact))
            }else{
                ContactList(
                    personList = personList,
                    onPersonClick = { onPersonClick(it.id) },
                    modifier = Modifier
                )
            }
    }
}

@Composable
private fun ContactList(
    personList: List<Person>,
    onPersonClick: (Person) -> Unit,
    modifier: Modifier = Modifier
){

    LazyColumn(modifier = modifier) {
        items(items = personList, key = {it.id}) { person ->
            ContactCard(
                person = person,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onPersonClick(person) }
            )
        }
    }

}

@Composable
fun ContactCard(
    person: Person,
    modifier: Modifier = Modifier
) {

}