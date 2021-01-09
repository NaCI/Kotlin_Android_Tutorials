package com.test.rxjavathrottleallclicks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.test.rxjavathrottleallclicks.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // We've set life ViewTreeLifecycleOwner to control lifecycle from binding adapter
        ViewTreeLifecycleOwner.set(window.decorView, this)

        binding.button1.setOnClickListener {
            startActivity(Intent(this, SubMainActivity::class.java))
        }
        binding.clickListener = ButtonClickListener { buttonName ->
            Timber.i("$buttonName clicked")
        }
    }
}