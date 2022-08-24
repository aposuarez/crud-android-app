package com.jmsuarez.crudsample.ui.noteDetails

import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState

sealed class NoteDetailsState: UIState() {

    data class Screen(
        val title: String,
        val description: String
    ): NoteDetailsState()

}

sealed class NoteDetailsEvent: UIEvent() {

    object EditNoteSuccess: NoteDetailsEvent()

    object EditNoteFailed: NoteDetailsEvent()

    object DeleteNoteSuccess: NoteDetailsEvent()

    object DeleteNoteFailed: NoteDetailsEvent()

}