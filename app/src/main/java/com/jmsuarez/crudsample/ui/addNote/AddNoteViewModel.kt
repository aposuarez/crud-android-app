package com.jmsuarez.crudsample.ui.addNote

import com.jmsuarez.crudsample.data.remote.NotesManager
import io.uniflow.android.AndroidDataFlow
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(private val notesManager: NotesManager) : AndroidDataFlow() {

    suspend fun addNewNote(title: String, description: String) {

        if(notesManager.addNote(title,description)) sendEvent(AddNoteEvent.AddNoteSuccess)
        else sendEvent(AddNoteEvent.AddNoteFailed)
    }
}