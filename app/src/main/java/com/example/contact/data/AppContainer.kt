package com.example.contact.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val personRepository: PersonRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflinePersonRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [PersonRepository]
     */
    override val personRepository: PersonRepository by lazy {
        OfflinePersonRepository(InventoryDatabase.getDatabase(context).personDao())
    }
}