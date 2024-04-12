package com.example.contact.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull
    val firstName: String,
    @NonNull
    val lastName: String,
    @NonNull
    val phoneNumber: String,
    @NonNull
    val email: String
)