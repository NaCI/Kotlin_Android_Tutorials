package com.test.rxjavathrottleallclicks

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import timber.log.Timber
import java.util.concurrent.TimeUnit

// Sorry - That didn't work - Dunno why - Most probably extension function writes statically on runtime
fun View.setOnClickListenerWithThrottle(listener: ((View) -> Unit)?) {
    this.clicks()
        .throttleFirst(CLICK_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
        .subscribe({
            this.setOnClickListener { view ->
                listener?.let {
                    listener(view)
                }
            }
        }, { throwable ->
            Timber.e(throwable, "Error throttling click")
        })
}