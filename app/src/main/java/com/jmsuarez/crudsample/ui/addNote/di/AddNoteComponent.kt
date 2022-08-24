package com.jmsuarez.crudsample.ui.addNote.di

import com.jmsuarez.crudsample.di.ActivityScope
import com.jmsuarez.crudsample.ui.addNote.AddNoteFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface AddNoteComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : AddNoteComponent
    }

    fun inject(fragment: AddNoteFragment)

}