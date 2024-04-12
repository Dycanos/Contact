package com.example.contact.data

import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun getAllPerson():Flow<List<Person>>

    fun getPersonById(id:Int):Flow<Person>

    suspend fun insertPerson(person: Person)

    suspend fun updatePerson(person: Person)

    suspend fun deletePerson(person: Person)
}