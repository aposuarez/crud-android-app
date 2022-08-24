package com.jmsuarez.crudsample.data.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jmsuarez.crudsample.data.local.Note
import kotlinx.coroutines.CompletableDeferred
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesManager @Inject constructor() {

    private val firestore = Firebase.firestore
    private val firebaseAuth = Firebase.auth

    suspend fun getNotes() : List<Note> {

        val resultDeferred = CompletableDeferred<List<Note>>()
        val notesList: MutableList<Note> = mutableListOf()

        firestore.collection(NOTES_COLLECTION_PATH)
            .whereEqualTo(KEY_USER_ID,firebaseAuth.currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    notesList.add(Note(
                        document.id,
                        document.data[KEY_NOTE_TITLE].toString(),
                        document.data[KEY_NOTE_DESCRIPTION].toString(),
                        document.data[KEY_NOTE_CREATED_AT].toString(),
                        document.data[KEY_NOTE_UPDATED_AT].toString()
                    ))
                }
                resultDeferred.complete(notesList)
            }
            .addOnFailureListener { resultDeferred.complete(notesList) }

        return resultDeferred.await()
    }

    suspend fun addNote(title: String, description: String) : Boolean {
        val resultDeferred = CompletableDeferred<Boolean>()

        val sdf = SimpleDateFormat("MMMM dd, YYYY @ hh:mm aaa")
        val currentDate = sdf.format(Date()).toString()

        val note = hashMapOf(
            KEY_USER_ID to firebaseAuth.currentUser?.uid,
            KEY_NOTE_CREATED_AT to currentDate,
            KEY_NOTE_TITLE to title,
            KEY_NOTE_DESCRIPTION to description,
            KEY_NOTE_UPDATED_AT to currentDate
        )

        firestore.collection(NOTES_COLLECTION_PATH)
            .add(note)
            .addOnSuccessListener { resultDeferred.complete(true) }
            .addOnFailureListener { resultDeferred.complete(false) }

        return resultDeferred.await()
    }

    suspend fun editNote(id: String, title: String, description: String) : Boolean {

        val resultDeferred = CompletableDeferred<Boolean>()

        val sdf = SimpleDateFormat("MMMM dd, YYYY @ hh:mm aaa")
        val currentDate = sdf.format(Date()).toString()

        val noteRef = firestore.collection(NOTES_COLLECTION_PATH).document(id)

        firestore.runBatch {
            it.apply {
                update(noteRef,KEY_NOTE_TITLE, title)
                update(noteRef, KEY_NOTE_DESCRIPTION, description)
                update(noteRef, KEY_NOTE_UPDATED_AT, currentDate)
            }
        }
            .addOnSuccessListener { resultDeferred.complete(true) }
            .addOnFailureListener { resultDeferred.complete(false) }

        return resultDeferred.await()
    }

    suspend fun deleteNote(id: String) : Boolean {
        val resultDeferred = CompletableDeferred<Boolean>()

        firestore.collection(NOTES_COLLECTION_PATH).document(id)
            .delete()
            .addOnSuccessListener { resultDeferred.complete(true) }
            .addOnFailureListener { resultDeferred.complete(false) }

        return resultDeferred.await()
    }

    companion object {

        private const val NOTES_COLLECTION_PATH = "notes"

        private const val KEY_USER_ID = "userId"

        private const val KEY_NOTE_TITLE = "noteTitle"

        private const val KEY_NOTE_DESCRIPTION = "noteDescription"

        private const val KEY_NOTE_CREATED_AT = "createdAt"

        private const val KEY_NOTE_UPDATED_AT = "updatedAt"

    }
}