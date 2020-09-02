package com.naci.handlingsoftkeyboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naci.handlingsoftkeyboard.ui.main.MainFragment

/**
 * Most important thing to handle sticky bottom button is create another layout to separate
 * screen content and button
 *
 * After that we need to use 'android:paddingBottom' property to give padding equal to button height
 *
 * And we can use android:clipChildren="false" and android:clipToPadding="false" properties
 * to make button edges transparent
 *
 * That's all!!
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}
