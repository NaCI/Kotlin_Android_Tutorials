package com.test.rxjavathrottleallclicks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.test.rxjavathrottleallclicks.databinding.ActivitySubMainBinding
import timber.log.Timber

class SubMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_main)

        ViewTreeLifecycleOwner.set(window.decorView, this)

        binding.button1.setOnClickListener {
            Timber.i("Button clicked - Another way")
        }
        binding.clickListener = ButtonClickListener { buttonName ->
            Timber.i("$buttonName clicked")
        }
    }
}