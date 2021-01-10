package com.test.rxjavathrottleallclicks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.test.rxjavathrottleallclicks.databinding.ActivityMainBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // We've set life ViewTreeLifecycleOwner to control lifecycle from binding adapter
        ViewTreeLifecycleOwner.set(window.decorView, this)

        // Listen events fired from rxBus
        listenRxBusEvent()

        // Get color based on current theme
        binding.button1.setBackgroundColor(
            getColorBasedOnTheme(
                R.attr.colorSecondary,
                R.color.teal_700
            )
        )

        binding.button1.setOnClickListener {
            startActivity(Intent(this, SubMainActivity::class.java))
        }
        binding.clickListener = ButtonClickListener { buttonName ->
            Timber.i("$buttonName clicked")
        }
    }

    private fun listenRxBusEvent() {
        compositeDisposable.addAll(
            RxBus.listen(RxEvent.MyEvent1::class.java).subscribe { myEvent1 ->
                Toast.makeText(this, "MyEvent1 Fired. Data: ${myEvent1.name}", Toast.LENGTH_SHORT)
                    .show()
                Timber.i("MyEvent1 Fired. Data: ${myEvent1.name}")
            },
            RxBus.listen(RxEvent.MyEvent2::class.java).subscribe { myEvent2 ->
                // PROBLEM !! This code block is triggering before activity onStart called. So nothing is changing on UI
                Snackbar.make(
                    binding.root,
                    "MyEvent2 Fired. Data: ${myEvent2.number} ${myEvent2.isItOk}",
                    Snackbar.LENGTH_LONG
                ).show()

                Timber.i("MyEvent2 Fired. Data: ${myEvent2.number} ${myEvent2.isItOk}")

                binding.button1.text = "asdasd"
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}