package com.jmsuarez.crudsample.ui.registration

import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState

sealed class RegistrationState: UIState() {

    data class Screen(
        val email: String,
        val password: String,
        val isSubmitButtonEnabled: Boolean
    ) : RegistrationState()
}

sealed class RegistrationEvent: UIEvent() {

    object EmailInputValid : RegistrationEvent()

    object EmailInputInvalid : RegistrationEvent()

    object PasswordInputValid : RegistrationEvent()

    object PasswordInputInvalid : RegistrationEvent()

    object RegistrationSuccess : RegistrationEvent()

    object RegistrationFailed : RegistrationEvent()

}