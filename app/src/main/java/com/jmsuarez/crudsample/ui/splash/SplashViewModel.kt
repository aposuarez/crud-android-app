package com.jmsuarez.crudsample.ui.splash

import androidx.lifecycle.ViewModel

class SplashViewModel() : ViewModel() {

    val splashScreenDelay: Long = 2000

    suspend fun checkActiveSession() : Boolean{
        //TODO: Check if user has active session.
        return false
    }

}