package com.jmsuarez.crudsample.utils.helpers

import android.util.Patterns
import java.util.regex.Pattern

object Validator {

    private val EMAIL_REQUIREMENT : Pattern = Patterns.EMAIL_ADDRESS

    private const val MIN_PASSWORD_LENGTH : Int = 8

    fun isEmailValid(email: String) = EMAIL_REQUIREMENT.matcher(email).matches()

    fun isPasswordValid(password: String) = password.length >= MIN_PASSWORD_LENGTH
}