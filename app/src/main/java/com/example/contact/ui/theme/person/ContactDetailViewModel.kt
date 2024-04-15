package com.example.contact.ui.theme.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.data.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ContactDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val personRepository: PersonRepository):ViewModel(){

    private val personId: Int = checkNotNull(savedStateHandle[ContactDetailsDestination.personIdArg])

    val uiState: StateFlow<PersonDetailsUiState> =
        personRepository.getPersonById(personId)
            .filterNotNull()
            .map {
                PersonDetailsUiState(personDetail = it.toPersonDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PersonDetailsUiState()
            )

    suspend fun deletePerson(){
        personRepository.deletePerson(uiState.value.personDetail.toItem())
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for ItemDetailsScreen
 */
data class PersonDetailsUiState(
    val personDetail: PersonDetails = PersonDetails()
)