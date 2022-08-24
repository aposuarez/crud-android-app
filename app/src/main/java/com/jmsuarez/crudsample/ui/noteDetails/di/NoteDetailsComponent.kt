package com.jmsuarez.crudsample.ui.noteDetails.di

import com.jmsuarez.crudsample.di.ActivityScope
import com.jmsuarez.crudsample.ui.noteDetails.NoteDetailsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface NoteDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): NoteDetailsComponent
    }

    fun inject(fragment: NoteDetailsFragment)

}