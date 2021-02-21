package com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.naci.tutorial.dependencyinjectionudemyclass.MyApplication

open class BaseActivity : AppCompatActivity() {
    val compositionRoot get() = (application as MyApplication).appCompositionRoot
}