package com.test.rxjavathrottleallclicks

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
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

fun Activity.getColorBasedOnTheme(
    @AttrRes themeColorResId: Int,
    @ColorRes defaultColorResId: Int
): Int {
    return MaterialColors.getColor(
        this,
        themeColorResId,
        ContextCompat.getColor(this, defaultColorResId)
    )
}

fun TextView.formatToMarkdownText(
    content: String
) {
    val spannable = SpannableStringBuilder()
    var startIndex = 0
    val matches = MARKDOWN_LINK_REGEX.toRegex().findAll(content)
    for (match in matches) {
        spannable.append(content.substring(startIndex, match.range.first))
        val firstIndexBeforeLink = spannable.length
        spannable.append(match.groupValues[1])
        val link = match.groupValues[2]
        val lastIndex = firstIndexBeforeLink + match.groupValues[1].length
        spannable.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    context.startActivity(browserIntent)
                }
            },
            firstIndexBeforeLink,
            lastIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        startIndex = match.range.last + 1
    }
    spannable.append(content.substring(startIndex))
    text = spannable
    movementMethod = LinkMovementMethod.getInstance()
}