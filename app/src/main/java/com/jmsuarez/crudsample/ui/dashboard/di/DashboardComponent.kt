package com.jmsuarez.crudsample.ui.dashboard.di

import com.jmsuarez.crudsample.di.ActivityScope
import com.jmsuarez.crudsample.ui.dashboard.DashboardFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface DashboardComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardComponent
    }

    fun inject(fragment: DashboardFragment)
}