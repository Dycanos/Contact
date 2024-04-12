package com.example.contact.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object{

        @Volatile
        private var Instance: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ContactDatabase::class.java, "person_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }
    }

}