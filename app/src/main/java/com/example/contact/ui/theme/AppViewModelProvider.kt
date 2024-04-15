package com.example.contact.ui.theme

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.contact.ContactApplication
import com.example.contact.ui.theme.home.HomeViewModel
import com.example.contact.ui.theme.person.ContactAddViewModel
import com.example.contact.ui.theme.person.ContactUpdateViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(personRepository = inventoryApplication().container.personRepository)
        }
        initializer {
            ContactAddViewModel(personRepository = inventoryApplication().container.personRepository)
        }
        initializer {
            ContactUpdateViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                personRepository = inventoryApplication().container.personRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [ContactApplication].
 */
fun CreationExtras.inventoryApplication(): ContactApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ContactApplication)
