package com.example.contact.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface PersonDao {

    /**
     * get all persons in the database
     */
    @Query(" SELECT * FROM person order by lastName ASC ")
    fun getAllPersons(): Flow<List<Person>>

    /**
     * get one person with this [id] in the database
     */
    @Query("SELECT * FROM person WHERE id = :id ")
    fun getPersonById(id:Int): Flow<Person>

    /**
     * insert one person in the database
     */
    @Insert
    fun insertPerson(person: Person)

    /**
     * delete one person from the database
     */
    @Delete
    fun deletePerson(person: Person)

    /**
     * update one person in the database
     */
    @Update
    fun updatePerson(person: Person)
}