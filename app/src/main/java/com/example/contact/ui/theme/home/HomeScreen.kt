package com.example.contact.ui.theme.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contact.ContactTopAppBar
import com.example.contact.data.Person
import com.example.contact.ui.theme.AppViewModelProvider
import com.example.contact.ui.theme.navigation.NavigationDestination
import com.example.contact.R

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
    val onSearchChanged: (String) -> Unit = { viewModel.updateUiState(it) }
    val onSearchKeywordChanged: (String) -> Unit = { viewModel.updateSearchKeyword(it) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ContactTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPersonEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)))
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.contact_entry_title)
                )
            }
        }
    ) {innerPadding ->

        Column(modifier = Modifier) {
            HomeBody(
                personList = homeUiState.personList,
                onPersonClick = navigateToPersonUpdate,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onChangeValue = onSearchChanged,
                updateSearchKeyword = onSearchKeywordChanged
            )
        }

    }

}

@Composable
private fun HomeBody(
    personList:List<Person>,
    onPersonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onChangeValue: (String)->Unit,
    updateSearchKeyword: (String) ->Unit
){

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (personList.isEmpty()){
                SearchBar(modifier = Modifier,onChangeValue = onChangeValue,updateSearchKeyword = updateSearchKeyword)
                Text(text = stringResource(R.string.no_contact))
            }else{
                SearchBar(modifier = Modifier,onChangeValue = onChangeValue,updateSearchKeyword = updateSearchKeyword)
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
                    .fillMaxWidth()
            )
        }
    }

}

@Composable
fun ContactCard(
    person: Person,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(2.dp,MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
            PersonIdentity(person, modifier = Modifier, fontSize = 15.sp)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
            PersonPhoneNumber(person, modifier = Modifier, fontSize = 15.sp)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
            PersonEmail(person, modifier = Modifier, fontSize = 15.sp)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_detail)))
        }
    }
}

@Composable
fun PersonEmail(person: Person, modifier: Modifier = Modifier,fontSize: TextUnit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Email, contentDescription = null)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extraSmall)))
        Text(text = person.email,fontSize = fontSize)
    }
}

@Composable
fun PersonPhoneNumber(person: Person, modifier: Modifier = Modifier,fontSize: TextUnit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Phone, contentDescription = null)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extraSmall)))
        Text(text = person.phoneNumber,fontSize = fontSize)
    }
}

@Composable
fun PersonIdentity(person: Person,modifier: Modifier = Modifier,fontSize: TextUnit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(imageVector = Icons.Default.Person, contentDescription = null)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extraSmall)))
        Text(text = person.firstName,fontSize = fontSize)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extraSmall)))
        Text(text = person.lastName,fontSize = fontSize)
    }
}
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onChangeValue: (String)->Unit,
    updateSearchKeyword: (String)->Unit
){
    var search by remember { mutableStateOf("")}
    TextField(
        value = search,
        onValueChange = {
            search = it
            onChangeValue(search)
            updateSearchKeyword(it)
                        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = null)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(stringResource(id = R.string.placeholder))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
        ,
        singleLine = true
        )
}

@Preview
@Composable
fun SearchBarPreview(){
    SearchBar(onChangeValue = {}, updateSearchKeyword = {})
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeBody(personList = listOf(Person(0,"test","nom","0909999999","cccc@ccc.com")), onPersonClick = {}, onChangeValue = {}, updateSearchKeyword = {})
}

@Preview
@Composable
fun CardPreview(){
    ContactCard(person = Person(0,"Louis","JACQUES","0616157153","louis.jacques@soprasteria.com"),Modifier.fillMaxWidth())}