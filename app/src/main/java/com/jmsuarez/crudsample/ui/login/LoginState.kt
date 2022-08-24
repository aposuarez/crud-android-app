package com.jmsuarez.crudsample.ui.login

import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState

sealed class LoginState: UIState() {

    data class Screen(
        val email: String,
        val password: String,
        val isSubmitButtonEnabled: Boolean
    ) : LoginState()
}

sealed class LoginEvent : UIEvent() {

    object EmailInputValid : LoginEvent()

    object EmailInputInvalid : LoginEvent()

    object PasswordInputValid : LoginEvent()

    object PasswordInputInvalid : LoginEvent()

    object LoginSuccess : LoginEvent()

    object LoginFailed : LoginEvent()

}