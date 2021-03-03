package com.naci.tutorial.dependencyinjectionudemyclass.common.di.app

import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.activity.ActivityModule
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.service.ServiceComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.service.ServiceModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponentBuilder(): ActivityComponent.Builder

    fun newServiceComponent(serviceModule: ServiceModule): ServiceComponent
}