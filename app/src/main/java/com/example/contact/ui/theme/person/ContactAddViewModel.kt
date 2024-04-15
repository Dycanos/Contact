package com.example.contact.ui.theme.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.contact.data.Person
import com.example.contact.data.PersonRepository

class ContactAddViewModel (private val personRepository: PersonRepository):ViewModel(){

    var personUiState by mutableStateOf(PersonUiState())
        private set


    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
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

    suspend fun savePerson(){
        if (validateInput()){
            personRepository.insertPerson(personUiState.personDetails.toItem())
        }
    }
}


/**
 * Represents Ui State for an Item.
 */
data class PersonUiState(
    val personDetails: PersonDetails = PersonDetails(),
    val isAddValid: Boolean = false
)


data class PersonDetails(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
)

fun PersonDetails.toItem(): Person = Person(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    email = email,
)
/**
 * Extension function to convert [Person] to [PersonUiState]
 */
fun Person.toItemUiState(isEntryValid: Boolean = false): PersonUiState = PersonUiState(
    personDetails = this.toPersonDetails(),
    isAddValid = isEntryValid
)

fun Person.toPersonDetails(): PersonDetails = PersonDetails(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    email = email,
)