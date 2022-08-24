package com.jmsuarez.crudsample.ui.noteDetails

import com.jmsuarez.crudsample.data.remote.NotesManager
import io.uniflow.android.AndroidDataFlow
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(private val notesManager: NotesManager): AndroidDataFlow() {

    suspend fun areInputsValid(title: String, description: String) {

        if (title.isNotBlank() && description.isNotBlank()) {
            setState {
                NoteDetailsState.Screen(
                    title = title,
                    description = description
                )
            }
        }
        else sendEvent(NoteDetailsEvent.EditNoteFailed)
    }

    suspend fun editNote(id: String, title: String, description: String) {
        if (notesManager.editNote(id, title, description)) sendEvent(NoteDetailsEvent.EditNoteSuccess)
        else sendEvent(NoteDetailsEvent.EditNoteFailed)
    }

    suspend fun deleteNote(id: String) {
        if (notesManager.deleteNote(id)) sendEvent(NoteDetailsEvent.DeleteNoteSuccess)
        else sendEvent(NoteDetailsEvent.DeleteNoteFailed)
    }

}