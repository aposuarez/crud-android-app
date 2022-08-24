package com.jmsuarez.crudsample.di

import android.content.Context
import com.jmsuarez.crudsample.main.MainActivity
import com.jmsuarez.crudsample.ui.addNote.di.AddNoteComponent
import com.jmsuarez.crudsample.ui.dashboard.di.DashboardComponent
import com.jmsuarez.crudsample.ui.login.di.LoginComponent
import com.jmsuarez.crudsample.ui.noteDetails.di.NoteDetailsComponent
import com.jmsuarez.crudsample.ui.registration.di.RegistrationComponent
import com.jmsuarez.crudsample.ui.splash.di.SplashComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(activity: MainActivity)

    fun splashComponent(): SplashComponent.Factory
    fun loginComponent(): LoginComponent.Factory
    fun registrationComponent(): RegistrationComponent.Factory
    fun dashboardComponent(): DashboardComponent.Factory
    fun addNoteComponent(): AddNoteComponent.Factory
    fun noteDetailsComponent(): NoteDetailsComponent.Factory
}