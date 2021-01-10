package com.test.rxjavathrottleallclicks

import android.app.Activity
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
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

fun Activity.getColorBasedOnTheme(@AttrRes themeColorResId: Int, @ColorRes defaultColorResId: Int): Int {
    return MaterialColors.getColor(this, themeColorResId, ContextCompat.getColor(this, defaultColorResId))
}