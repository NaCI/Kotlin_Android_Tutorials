package com.naci.tutorial.dependencyinjectionudemyclass.screens.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.naci.tutorial.dependencyinjectionudemyclass.R
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.ScreensNavigator
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity.BaseActivity
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.toolbar.MyToolbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewModelActivity : BaseActivity() {

    @Inject
    lateinit var screensNavigator: ScreensNavigator

    // New style initialization
    private val myViewModel: MyViewModel by viewModels()

    // Old-fashioned initialization
    private lateinit var myViewModel2: MyViewModel2

    private lateinit var toolbar: MyToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_view_model)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            screensNavigator.navigateBack()
        }

        myViewModel2 = ViewModelProvider(this).get(MyViewModel2::class.java)

        myViewModel.questions.observe(this, Observer { questions ->
            Toast.makeText(this, "fetched ${questions.size} questions", Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ViewModelActivity::class.java)
            context.startActivity(intent)
        }
    }
}