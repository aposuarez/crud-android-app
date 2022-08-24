package com.jmsuarez.crudsample.ui.registration

import com.jmsuarez.crudsample.data.remote.UserManager
import com.jmsuarez.crudsample.utils.helpers.Validator
import io.uniflow.android.AndroidDataFlow
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(private val userManager: UserManager): AndroidDataFlow() {

    suspend fun isEmailInputValid(email: String) {
        if (Validator.isEmailValid(email)) sendEvent(RegistrationEvent.EmailInputValid)
        else sendEvent(RegistrationEvent.EmailInputInvalid)
    }

    suspend fun isPasswordInputValid(password: String) {
        if (Validator.isPasswordValid(password)) sendEvent(RegistrationEvent.PasswordInputValid)
        else sendEvent(RegistrationEvent.PasswordInputInvalid)
    }

    suspend fun verifyInputs(params: Params) {

        val isButtonEnabled =  Validator.isEmailValid(params.email) && Validator.isPasswordValid(params.password)

        setState {
            RegistrationState.Screen(
                email = params.email,
                password = params.password,
                isSubmitButtonEnabled = isButtonEnabled
            )
        }
    }

    suspend fun registerUserEmail(email: String, password: String) {
        if(userManager.registerEmail(email,password)) sendEvent(RegistrationEvent.RegistrationSuccess)
        else sendEvent(RegistrationEvent.RegistrationFailed)
    }

    companion object Registration {

        data class Params(val email: String, val password: String)

    }
}