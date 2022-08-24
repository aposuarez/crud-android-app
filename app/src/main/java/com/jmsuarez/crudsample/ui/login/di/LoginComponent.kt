package com.jmsuarez.crudsample.ui.login.di

import com.jmsuarez.crudsample.di.ActivityScope
import com.jmsuarez.crudsample.ui.login.LoginFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): LoginComponent
    }

    fun inject(fragment: LoginFragment)
}