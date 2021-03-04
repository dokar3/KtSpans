package com.dokar.ktspans.spans

import android.text.style.ClickableSpan
import android.view.View

class ClickableSpanImpl(private val onClick: (View) -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) {
        onClick.invoke(widget)
    }
}