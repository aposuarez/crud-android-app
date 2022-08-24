package com.jmsuarez.crudsample.ui.addNote

import io.uniflow.core.flow.data.UIEvent

sealed class AddNoteEvent : UIEvent() {

    object AddNoteSuccess : AddNoteEvent()

    object AddNoteFailed : AddNoteEvent()

}