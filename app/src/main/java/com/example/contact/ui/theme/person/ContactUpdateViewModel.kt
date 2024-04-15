package com.example.contact.ui.theme.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.data.PersonRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ContactUpdateViewModel (savedStateHandle: SavedStateHandle, private val personRepository: PersonRepository): ViewModel(){

    var personUiState by mutableStateOf(PersonUiState())
        private set

    var personId: Int = checkNotNull(savedStateHandle[ContactUpdateDestination.personIdArg])

    /**
     * Updates the [personUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(personDetails: PersonDetails) {
        personUiState =
            PersonUiState(personDetails = personDetails, isAddValid = validateInput(personDetails))
    }

    private fun validateInput(uiState: PersonDetails = personUiState.personDetails): Boolean {
        return with(uiState) {
            firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank() && email.isNotBlank()
        }
    }

    suspend fun updatePerson(){
        if (validateInput()){
            personRepository.updatePerson(personUiState.personDetails.toItem())
        }
    }

    init {
        viewModelScope.launch {
            personUiState = personRepository.getPersonById(personId)
                .filterNotNull()
                .first()
                .toItemUiState(true)
        }
    }
}