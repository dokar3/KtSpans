package com.dokar.ktspansexample

import com.dokar.ktspans.*
import com.dokar.ktspansexample.spans.DashedHrSpan

// extension function for span dsl
@SpanTagMarker
inline fun Tag.dashedHr(crossinline body: DashedHr.() -> Unit) {
    val tag = DashedHr(root).also(body)
    // Generate default attributes if it does not exists.
    root!!.defaultStyle.dashedHr {}
    // Apply attributes
    val attrs = (tag.attrs ?: root!!.attrs(tag.name)) as DashedHrAttributes
    val height = attrs.height.value
    // color is built-in attr in Attributes
    val color = attrs.color
    // Insert into parent tag, if isBlockElement = true, a new line
    // will be inserted after tag
    insertTag(tag, arrayOf(DashedHrSpan(height, color)), isBlockElement = true)
}

// extension function for style dsl
inline fun StyleSheet.dashedHr(body: DashedHrAttributes.() -> Unit) {
    val attrs = tagAttrs[DashedHr.NAME] as? DashedHrAttributes
        ?: DashedHrAttributes().also {
            tagAttrs[DashedHr.NAME] = it
        }
    attrs.body()
}

// extension function for fullAttrs
@StyleTagMarker
inline fun DashedHr.fullAttrs(body: DashedHrAttributes.() -> Unit) {
    this.attrs = DashedHrAttributes().also(body)
}

// custom attributes
class DashedHrAttributes(var height: Size = 1.dp) : Attributes()

// custom tag class
class DashedHr(root: Root?) : SelfClosingTag(NAME, root) {
    companion object {
        const val NAME = "dashed-hr"
    }
}