package com.dokar.ktspans

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.*
import android.view.View
import com.dokar.ktspans.spans.*

@DslMarker
annotation class SpanTagMarker

@DslMarker
annotation class StyleTagMarker

inline fun createSpanned(body: RootTag.() -> Unit): Spanned {
    val root = RootTag()
    root.body()
    return root.span()
}

inline fun createStyle(body: StyleSheet.() -> Unit): StyleSheet {
    val style = StyleSheet()
    style.body()
    return style
}

fun checkRoot(tag: Tag) {
    if (tag.root == null) {
        throw IllegalAccessException("This tag ${tag.name}{} must be inside createSpan {}.")
    }
}

fun Tag.insertTag(tag: Tag, specificSpans: Array<out Any>?, isBlockElement: Boolean = false) {
    if (tag.span().isEmpty()) {
        return
    }

    val spanBuilder = spanBuilder()

    if (spanBuilder.isNotEmpty() && isBlockElement && spanBuilder.last() != '\n') {
        spanBuilder.appendLine()
    }

    spanBuilder.append(tag.span())

    val start = spanBuilder.length - tag.span().length
    val end = spanBuilder.length
    val flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

    if (specificSpans != null) {
        for (span in specificSpans) {
            spanBuilder.setSpan(span, start, end, flags)
        }
    }

    // apply global attributes
    root!!.attrs(tag.name)?.let {
        applyAttributes(spanBuilder, it, start, end, flags)
    }

    // apply class attributes
    root!!.style?.tagAttrs?.get(tag.className)?.let {
        applyAttributes(spanBuilder, it, start, end, flags)
    }

    // apply tag attributes
    tag.attrs?.let {
        applyAttributes(spanBuilder, it, start, end, flags)
    }

    if (isBlockElement) {
        spanBuilder.appendLine()
    }
}

fun applyAttributes(
    spanBuilder: SpannableStringBuilder,
    attrs: Attributes,
    start: Int,
    end: Int,
    flags: Int
) {
    val size = attrs.fontSize
    if (size is Px) {
        spanBuilder.setSpan(AbsoluteSizeSpan(size.value.toInt()), start, end, flags)
    } else if (size is Em) {
        spanBuilder.setSpan(RelativeSizeSpan(size.value), start, end, flags)
    }

    val fontFamily = attrs.fontFamily
    if (fontFamily != null) {
        spanBuilder.setSpan(TypefaceSpanCompat.create(fontFamily), start, end, flags)
    }

    val fontStyle = attrs.fontStyle
    if (fontStyle != Typeface.NORMAL) {
        spanBuilder.setSpan(StyleSpan(fontStyle), start, end, flags)
    }

    val lineHeight = attrs.lineHeight
    if (lineHeight is Px) {
        spanBuilder.setSpan(StdLineHeightSpan(lineHeight.value.toInt()), start, end, flags)
    } else if (lineHeight is Em) {
        spanBuilder.setSpan(StdLineHeightSpan(lineHeight.value), start, end, flags)
    }

    val color = attrs.color
    if (color != 0) {
        spanBuilder.setSpan(ForegroundColorSpan(color), start, end, flags)
    }

    val backgroundColor = attrs.backgroundColor
    if (backgroundColor != 0) {
        spanBuilder.setSpan(BackgroundColorSpan(backgroundColor), start, end, flags)
    }

    val decoration = attrs.decoration
    if (decoration == Decoration.Underline) {
        spanBuilder.setSpan(UnderlineSpan(), start, end, flags)
    } else if (decoration == Decoration.Strikethrough) {
        spanBuilder.setSpan(StrikethroughSpan(), start, end, flags)
    }

    val alignment = attrs.align
    if (alignment != null) {
        spanBuilder.setSpan(AlignmentSpan { alignment }, start, end, flags)
    }
}

@StyleTagMarker
inline fun RootTag.style(body: StyleSheet.() -> Unit) {
    this.style = StyleSheet().also(body)
}

@StyleTagMarker
fun RootTag.style(style: StyleSheet?) {
    this.style = style
}

@StyleTagMarker
inline fun Tag.attrs(body: Attributes.() -> Unit) {
    val attrs = Attributes()
    attrs.body()
    this.attrs = attrs
}

@StyleTagMarker
inline fun Hr.fullAttrs(body: HrAttributes.() -> Unit) {
    val attrs = HrAttributes()
    attrs.body()
    this.attrs = attrs
}

@StyleTagMarker
inline fun Img.fullAttrs(body: ImgAttributes.() -> Unit) {
    val attrs = ImgAttributes()
    attrs.body()
    this.attrs = attrs
}

@StyleTagMarker
inline fun Bullet.fullAttrs(body: BulletAttributes.() -> Unit) {
    val attrs = BulletAttributes()
    attrs.body()
    this.attrs = attrs
}

@StyleTagMarker
inline fun Quote.fullAttrs(body: QuoteAttributes.() -> Unit) {
    val attrs = QuoteAttributes()
    attrs.body()
    this.attrs = attrs
}

@SpanTagMarker
inline fun Tag.h1(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(H1, root)
    tag.body()
    root!!.defaultStyle.h1 {}
    insertTag(tag, null, true)
}

@SpanTagMarker
inline fun Tag.h2(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(H2, root)
    tag.body()
    root!!.defaultStyle.h2 {}
    insertTag(tag, null, true)
}

@SpanTagMarker
inline fun Tag.h3(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(H3, root)
    tag.body()
    root!!.defaultStyle.h3 {}
    insertTag(tag, null, true)
}

@SpanTagMarker
inline fun Tag.h4(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(H4, root)
    tag.body()
    root!!.defaultStyle.h4 {}
    insertTag(tag, null, true)
}

@SpanTagMarker
inline fun Tag.h5(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(H5, root)
    tag.body()
    root!!.defaultStyle.h5 {}
    insertTag(tag, null, true)
}

@SpanTagMarker
inline fun Tag.h6(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(H6, root)
    tag.body()
    root!!.defaultStyle.h6 {}
    insertTag(tag, null, true)
}

@SpanTagMarker
inline fun Tag.br(crossinline body: SelfClosingTag.() -> Unit) {
    checkRoot(this)
    spanBuilder().appendLine()
}

@SpanTagMarker
inline fun Tag.p(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(P, root)
    tag.body()
    root!!.defaultStyle.p {}
    insertTag(tag, null, true)
}

@SpanTagMarker
inline fun Tag.span(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(SPAN, root)
    tag.body()
    root!!.defaultStyle.p {}
    insertTag(tag, null)
}

@SpanTagMarker
inline fun Tag.b(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(B, root)
    tag.body()
    root!!.defaultStyle.b {}
    insertTag(tag, null)
}

@SpanTagMarker
inline fun Tag.i(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(I, root)
    tag.body()
    root!!.defaultStyle.i {}
    insertTag(tag, null)
}

@SpanTagMarker
inline fun Tag.u(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(U, root)
    tag.body()
    root!!.defaultStyle.u {}
    insertTag(tag, null)
}

@SpanTagMarker
inline fun Tag.s(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(S, root)
    tag.body()
    root!!.defaultStyle.s {}
    insertTag(tag, null)
}

@SpanTagMarker
inline fun Tag.a(url: String, crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(A, root)
    tag.body()
    root!!.defaultStyle.a {}
    insertTag(tag, arrayOf(URLSpan(url)))
}

@SpanTagMarker
inline fun Tag.hr(crossinline body: SelfClosingTag.() -> Unit) {
    checkRoot(this)
    val tag = SelfClosingTag(HR, root)
    tag.body()

    root!!.defaultStyle.hr {}

    val attrs = (tag.attrs as? HrAttributes) ?: root!!.attrs(tag.name) as HrAttributes
    val span = HrSpan(attrs.height.value, attrs.color)

    insertTag(tag, arrayOf(span), true)
}

@SpanTagMarker
inline fun Tag.sup(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(SUP, root)
    tag.body()
    root!!.defaultStyle.sup {}
    insertTag(tag, arrayOf(SuperscriptSpan()))
}

@SpanTagMarker
inline fun Tag.sub(crossinline body: Tag.() -> Unit) {
    checkRoot(this)
    val tag = Tag(SUB, root)
    tag.body()
    root!!.defaultStyle.sub {}
    insertTag(tag, arrayOf(SubscriptSpan()))
}

@SpanTagMarker
inline fun Tag.img(
    drawable: Drawable,
    crossinline body: Img.() -> Unit
) {
    checkRoot(this)
    val tag = Img(IMG, root)
    tag.body()
    root!!.defaultStyle.img {}

    val attrs = (tag.attrs as? ImgAttributes) ?: root!!.attrs(tag.name) as ImgAttributes

    val ratio = drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight
    var w = attrs.width
    var h = attrs.height
    if (w <= 0 && h <= 0) {
        w = drawable.intrinsicWidth
        h = drawable.intrinsicHeight
    } else if (w <= 0) {
        w = (ratio * h).toInt()
    } else {
        h = (w / ratio).toInt()
    }
    drawable.setBounds(0, 0, w, h)

    val verticalAlignment = attrs.verticalAlignment

    insertTag(tag, arrayOf(ImageSpan(drawable, verticalAlignment)))
}

@SpanTagMarker
inline fun Tag.bullet(
    body: Bullet.() -> Unit
) {
    checkRoot(this)
    val tag = Bullet(BULLET, root)
    tag.body()
    root!!.defaultStyle.bullet {}

    val attrs = (tag.attrs as? BulletAttributes) ?: root!!.attrs(tag.name) as BulletAttributes
    val span = BulletSpanCompat.create(
        attrs.gapWidth.value.toInt(),
        attrs.bulletColor,
        attrs.bulletRadius.value.toInt()
    )

    insertTag(tag, arrayOf(span), true)
}

@SpanTagMarker
inline fun Tag.quote(
    crossinline body: Quote.() -> Unit
) {
    checkRoot(this)
    val tag = Quote(QUOTE, root)
    tag.body()
    root!!.defaultStyle.quote {}

    val attrs = (tag.attrs as? QuoteAttributes) ?: root!!.attrs(tag.name) as QuoteAttributes
    val span = QuoteSpanCompat.create(
        attrs.stripeColor,
        attrs.stripeWidth.value.toInt(),
        attrs.gapWidth.value.toInt()
    )

    insertTag(tag, arrayOf(span), true)
}

@SpanTagMarker
inline fun Tag.clickable(
    noinline onClick: (v: View) -> Unit,
    crossinline body: Tag.() -> Unit
) {
    checkRoot(this)
    val tag = Tag(CLICKABLE, root)
    tag.body()
    root!!.defaultStyle.clickable {}
    insertTag(tag, arrayOf(ClickableSpanImpl(onClick)))
}
