package com.dokar.ktspans

class StyleSheet {

    val tagAttrs: MutableMap<String, Attributes> = HashMap()

    inline operator fun String.invoke(body: Attributes.() -> Unit) {
        val attrs = tagAttrs[this] ?: Attributes().also {
            tagAttrs[this] = it
        }
        attrs.body()
    }
}
