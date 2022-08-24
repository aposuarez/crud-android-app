package com.jmsuarez.crudsample.ui.login

import com.jmsuarez.crudsample.data.remote.UserManager
import com.jmsuarez.crudsample.utils.helpers.Validator
import io.uniflow.android.AndroidDataFlow
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userManager: UserManager) : AndroidDataFlow(){

    suspend fun isEmailInputValid(email: String) {
        if (Validator.isEmailValid(email)) sendEvent(LoginEvent.EmailInputValid)
        else sendEvent(LoginEvent.EmailInputInvalid)
    }

    suspend fun isPasswordInputValid(password: String) {
        if (Validator.isPasswordValid(password)) sendEvent(LoginEvent.PasswordInputValid)
        else sendEvent(LoginEvent.PasswordInputInvalid)
    }

    suspend fun verifyInputs(params: Params) {

        val isButtonEnabled =  Validator.isEmailValid(params.email) && Validator.isPasswordValid(params.password)

        setState {
            LoginState.Screen(
                email = params.email,
                password = params.password,
                isSubmitButtonEnabled = isButtonEnabled
            )
        }
    }

    suspend fun loginEmail(email:String, password: String) {
        if(userManager.loginEmail(email,password)) sendEvent(LoginEvent.LoginSuccess)
        else sendEvent(LoginEvent.LoginFailed)
    }

    companion object Login {

        data class Params(val email: String, val password: String)

    }
}