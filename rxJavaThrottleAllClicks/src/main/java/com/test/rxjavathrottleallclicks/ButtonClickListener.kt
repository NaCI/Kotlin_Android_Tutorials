package com.test.rxjavathrottleallclicks

class ButtonClickListener(val clickListener: (buttonNumber: String) -> Unit) {
    fun onClick(buttonNumber: String) = clickListener(buttonNumber)
}