package com.jmsuarez.crudsample.data.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor() {

    private val firebaseAuth = Firebase.auth

    fun checkUserSession() = firebaseAuth.currentUser

    suspend fun registerEmail(email: String, password: String): Boolean {
        val resultDeferred = CompletableDeferred<Boolean>()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                resultDeferred.complete(task.isSuccessful)
            }

        return resultDeferred.await()
    }

    suspend fun loginEmail(email: String, password: String): Boolean {
        val resultDeferred = CompletableDeferred<Boolean>()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                resultDeferred.complete(task.isSuccessful)
            }

        return resultDeferred.await()
    }

    fun userLogout() = firebaseAuth.signOut()


}