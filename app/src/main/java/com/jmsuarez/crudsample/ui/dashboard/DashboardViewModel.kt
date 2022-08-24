package com.jmsuarez.crudsample.ui.dashboard

import com.jmsuarez.crudsample.data.remote.NotesManager
import com.jmsuarez.crudsample.data.remote.UserManager
import io.uniflow.android.AndroidDataFlow
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val userManager: UserManager, private val notesManager: NotesManager) : AndroidDataFlow(){

    suspend fun getNotes() {

        val notesList = notesManager.getNotes()

        setState {
            DashboardState.Screen(
                notesList = notesList
            )
        }

    }

    fun userLogout() = userManager.userLogout()

}