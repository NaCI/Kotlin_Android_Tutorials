package com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity

import com.naci.tutorial.dependencyinjectionudemyclass.common.di.presentation.PresentationComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(): PresentationComponent
}