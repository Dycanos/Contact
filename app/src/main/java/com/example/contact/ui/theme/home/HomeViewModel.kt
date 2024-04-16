package com.example.contact.ui.theme.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.data.Person
import com.example.contact.data.PersonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val personRepository: PersonRepository):ViewModel(){

    private val _searchKeyword = MutableStateFlow("")

    var homeUiState: StateFlow<HomeUiState> = combine(_searchKeyword,personRepository.getAllPerson()){
        searchKeyword,personList -> if (searchKeyword.isEmpty()){
            HomeUiState(personList)
    }else{
        val filteredList = personList.filter { person -> person.lastName.contains(searchKeyword,ignoreCase = true) }
        HomeUiState(filteredList)
    }
    }.stateIn(scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState())



    val searchKeyword: StateFlow<String>
        get() {
            return _searchKeyword
        }

    fun updateSearchKeyword(str: String){
        _searchKeyword.value = str
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


}


/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val personList: List<Person> = listOf())