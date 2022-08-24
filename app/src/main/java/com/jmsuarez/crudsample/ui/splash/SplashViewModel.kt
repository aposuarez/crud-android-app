package com.jmsuarez.crudsample.ui.splash

import androidx.lifecycle.ViewModel
import com.jmsuarez.crudsample.data.remote.UserManager
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val userManager: UserManager) : ViewModel() {

    val splashScreenDelay: Long = 2000

    fun checkActiveSession() : Boolean = userManager.checkUserSession() != null

}