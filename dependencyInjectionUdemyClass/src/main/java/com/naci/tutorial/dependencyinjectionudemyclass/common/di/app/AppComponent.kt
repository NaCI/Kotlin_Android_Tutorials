package com.naci.tutorial.dependencyinjectionudemyclass.common.di.app

import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}