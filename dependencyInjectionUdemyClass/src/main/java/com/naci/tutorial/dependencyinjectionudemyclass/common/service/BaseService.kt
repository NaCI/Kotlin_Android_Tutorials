package com.naci.tutorial.dependencyinjectionudemyclass.common.service

import android.app.Service
import com.naci.tutorial.dependencyinjectionudemyclass.MyApplication
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.service.ServiceComponent
import com.naci.tutorial.dependencyinjectionudemyclass.common.di.service.ServiceModule

abstract class BaseService : Service() {
    private val appComponent get() = (application as MyApplication).appComponent

    private val serviceComponent: ServiceComponent by lazy {
        appComponent.newServiceComponent(ServiceModule(this))
    }

    protected val injector get() = serviceComponent
}