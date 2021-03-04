package com.dokar.ktspans

import android.text.SpannableStringBuilder
import android.text.Spanned

open class Tag(val name: String, var root: Root? = null) {

    var attrs: Attributes? = null

    var className: String? = null

    private val spanBuilder = SpannableStringBuilder()

    open fun span(): Spanned {
        return spanBuilder
    }

    open fun spanBuilder(): SpannableStringBuilder {
        return spanBuilder
    }

    open operator fun String.unaryPlus() {
        spanBuilder.append(this)
    }

    open operator fun Spanned.unaryPlus() {
        spanBuilder.append(this)
    }
}