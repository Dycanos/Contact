package com.example.contact.ui.theme.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.data.Person
import com.example.contact.data.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val personRepository: PersonRepository):ViewModel(){

    var homeUiState: StateFlow<HomeUiState> =
        personRepository.getAllPerson().map {
            HomeUiState(it)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    var searchUiState by mutableStateOf("")
        private set

    fun updateUiState(search: String) {
        searchUiState = search
    }

    fun updateSearchKeyword(str: String){
        homeUiState =
        personRepository.getPersonByKeyword(str).map {
            HomeUiState(it)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


}


/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val personList: List<Person> = listOf())