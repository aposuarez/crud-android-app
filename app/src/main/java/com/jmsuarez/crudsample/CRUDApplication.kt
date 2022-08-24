package com.jmsuarez.crudsample

import android.app.Application
import com.jmsuarez.crudsample.di.AppComponent
import com.jmsuarez.crudsample.di.DaggerAppComponent

class CRUDApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}