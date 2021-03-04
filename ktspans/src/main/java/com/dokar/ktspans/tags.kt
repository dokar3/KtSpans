package com.dokar.ktspans

import android.text.SpannableStringBuilder
import android.text.Spanned

const val H1 = "h1"
const val H2 = "h2"
const val H3 = "h3"
const val H4 = "h4"
const val H5 = "h5"
const val H6 = "h6"
const val P = "p"
const val I = "i"
const val B = "b"
const val U = "u"
const val S = "s"
const val A = "a"
const val HR = "hr"
const val SPAN = "span"
const val IMG = "img"
const val SUP = "sup"
const val SUB = "sub"
const val BULLET = "bullet"
const val QUOTE = "quote"
const val CLICKABLE = "clickable"

class Bullet(name: String, root: Root?) : Tag(name, root)

class Quote(name: String, root: Root?) : Tag(name, root)

class Hr(name: String, root: Root?) : SelfClosingTag(name, root)

class Img(name: String, root: Root?) : SelfClosingTag(name, root)

open class SelfClosingTag(name: String, root: Root?) : Tag(name, root) {

    private val content = SpannableStringBuilder(" ")

    @Deprecated("This tag does not support text content.")
    override operator fun String.unaryPlus() {
    }

    @Deprecated("This tag does not support text content.")
    override operator fun Spanned.unaryPlus() {
    }

    override fun span(): Spanned {
        return content
    }
}

class RootTag : Tag("root"), Root {

    init {
        root = this
    }

    private val _defaultStyle = StyleSheet()

    private var _style: StyleSheet? = null

    override val defaultStyle: StyleSheet
        get() = _defaultStyle

    override var style: StyleSheet?
        get() = _style
        set(value) {
            _style = value
        }

    override fun attrs(tagName: String): Attributes? {
        return style?.tagAttrs?.get(tagName) ?: defaultStyle.tagAttrs[tagName]
    }
}