package com.jmsuarez.crudsample.ui.dashboard

import com.jmsuarez.crudsample.data.local.Note
import io.uniflow.core.flow.data.UIState

sealed class DashboardState : UIState() {

    data class Screen(
        val notesList: List<Note> = emptyList()
    ): DashboardState()

}