package com.example.contact.ui.theme.home

import androidx.compose.foundation.DefaultMarqueeDelayMillis
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.data.Person
import com.example.contact.data.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(personRepository: PersonRepository):ViewModel(){

    val homeUiState: StateFlow<HomeUiState> =
        personRepository.getAllPerson().map {HomeUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val personList: List<Person> = listOf())