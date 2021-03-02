package com.naci.tutorial.dependencyinjectionudemyclass.common.di.service

import dagger.Subcomponent

@ServiceScope
@Subcomponent(modules = [ServiceModule::class])
interface ServiceComponent {

}