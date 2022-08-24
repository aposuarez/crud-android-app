package com.jmsuarez.crudsample.ui.registration.di

import com.jmsuarez.crudsample.di.ActivityScope
import com.jmsuarez.crudsample.ui.registration.RegistrationFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface RegistrationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationComponent
    }

    fun inject(fragment: RegistrationFragment)
}
