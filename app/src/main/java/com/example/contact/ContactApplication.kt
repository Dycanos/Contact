package com.example.contact

import android.app.Application
import com.example.contact.data.AppContainer
import com.example.contact.data.AppDataContainer

class ContactApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}