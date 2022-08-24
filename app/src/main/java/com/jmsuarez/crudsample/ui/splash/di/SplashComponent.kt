package com.jmsuarez.crudsample.ui.splash.di

import com.jmsuarez.crudsample.di.ActivityScope
import com.jmsuarez.crudsample.ui.splash.SplashFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SplashComponent
    }

    fun inject(fragment: SplashFragment)
}