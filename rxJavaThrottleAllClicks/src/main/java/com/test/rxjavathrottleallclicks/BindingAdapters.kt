package com.test.rxjavathrottleallclicks

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.jakewharton.rxbinding4.view.clicks
import timber.log.Timber
import java.util.concurrent.TimeUnit


@BindingAdapter("android:onClick")
fun bindClick(view: View, listener: View.OnClickListener) {
    view.clicks()
//        .throttleFirst(CLICK_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
        .delay(CLICK_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
        .subscribe({
            // Check if view still alive to avoid crashes
            if (ViewTreeLifecycleOwner.get(view)?.lifecycle?.currentState?.isAtLeast(Lifecycle.State.STARTED)!!) {
                listener.onClick(view)
            }
        }, { throwable ->
            Timber.e(throwable, "Error throttling click")
        })
}
