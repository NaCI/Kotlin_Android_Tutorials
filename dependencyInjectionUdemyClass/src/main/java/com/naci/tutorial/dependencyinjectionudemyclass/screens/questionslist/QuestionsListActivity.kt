package com.naci.tutorial.dependencyinjectionudemyclass.screens.questionslist

import android.os.Bundle
import com.naci.tutorial.dependencyinjectionudemyclass.R
import com.naci.tutorial.dependencyinjectionudemyclass.screens.common.activity.BaseActivity

class QuestionsListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_content, QuestionsListFragment())
                .commit()
        }
    }
}