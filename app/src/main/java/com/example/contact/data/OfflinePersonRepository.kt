package com.example.contact.data

import kotlinx.coroutines.flow.Flow

class OfflinePersonRepository(private val personDao: PersonDao):PersonRepository {
    override fun getAllPerson(): Flow<List<Person>> {
        return personDao.getAllPersons()
    }

    override fun getPersonById(id: Int): Flow<Person> {
       return personDao.getPersonById(id)
    }

    override suspend fun insertPerson(person: Person) {
        personDao.insertPerson(person)
    }

    override suspend fun updatePerson(person: Person) {
        personDao.updatePerson(person)
    }

    override suspend fun deletePerson(person: Person) {
        personDao.deletePerson(person)
    }
}