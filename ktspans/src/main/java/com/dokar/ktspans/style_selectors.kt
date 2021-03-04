package com.dokar.ktspans

import android.graphics.Typeface

inline fun StyleSheet.h1(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[H1] ?: Attributes(
        fontSize = 2.em,
        fontStyle = Typeface.BOLD
    ).also {
        tagAttrs[H1] = it
    }
    attrs.body()
}

inline fun StyleSheet.h2(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[H2] ?: Attributes(
        fontSize = 1.8.em,
        fontStyle = Typeface.BOLD
    ).also {
        tagAttrs[H2] = it
    }
    attrs.body()
}

inline fun StyleSheet.h3(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[H3] ?: Attributes(
        fontSize = 1.6.em,
        fontStyle = Typeface.BOLD
    ).also {
        tagAttrs[H3] = it
    }
    attrs.body()
}

inline fun StyleSheet.h4(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[H4] ?: Attributes(
        fontSize = 1.4.em,
        fontStyle = Typeface.BOLD
    ).also {
        tagAttrs[H4] = it
    }
    attrs.body()
}

inline fun StyleSheet.h5(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[H5] ?: Attributes(
        fontSize = 1.2.em,
        fontStyle = Typeface.BOLD
    ).also {
        tagAttrs[H5] = it
    }
    attrs.body()
}

inline fun StyleSheet.h6(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[H6] ?: Attributes(
        fontSize = 1.em,
        fontStyle = Typeface.BOLD
    ).also {
        tagAttrs[H6] = it
    }
    attrs.body()
}

inline fun StyleSheet.i(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[I] ?: Attributes(
        fontStyle = Typeface.ITALIC
    ).also {
        tagAttrs[I] = it
    }
    attrs.body()
}

inline fun StyleSheet.b(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[B] ?: Attributes(
        fontStyle = Typeface.BOLD
    ).also {
        tagAttrs[B] = it
    }
    attrs.body()
}

inline fun StyleSheet.u(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[U] ?: Attributes(
        decoration = Decoration.Underline
    ).also {
        tagAttrs[U] = it
    }
    attrs.body()
}

inline fun StyleSheet.s(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[S] ?: Attributes(
        decoration = Decoration.Strikethrough
    ).also {
        tagAttrs[S] = it
    }
    attrs.body()
}

inline fun StyleSheet.p(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[P] ?: Attributes().also {
        tagAttrs[P] = it
    }
    attrs.body()
}

inline fun StyleSheet.a(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[A] ?: Attributes().also {
        tagAttrs[A] = it
    }
    attrs.body()
}

inline fun StyleSheet.hr(body: HrAttributes.() -> Unit) {
    val attrs = tagAttrs[HR] as? HrAttributes ?: HrAttributes().also {
        tagAttrs[HR] = it
    }
    attrs.body()
}

inline fun StyleSheet.span(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[SPAN] ?: Attributes().also {
        tagAttrs[SPAN] = it
    }
    attrs.body()
}

inline fun StyleSheet.sup(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[SUP] ?: Attributes().also {
        tagAttrs[SUP] = it
    }
    attrs.body()
}

inline fun StyleSheet.sub(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[SUP] ?: Attributes().also {
        tagAttrs[SUP] = it
    }
    attrs.body()
}

inline fun StyleSheet.img(body: ImgAttributes.() -> Unit) {
    val attrs = tagAttrs[IMG] as? ImgAttributes ?: ImgAttributes().also {
        tagAttrs[IMG] = it
    }
    attrs.body()
}

inline fun StyleSheet.bullet(body: BulletAttributes.() -> Unit) {
    val attrs = tagAttrs[BULLET] as? BulletAttributes ?: BulletAttributes().also {
        tagAttrs[BULLET] = it
    }
    attrs.body()
}

inline fun StyleSheet.quote(body: QuoteAttributes.() -> Unit) {
    val attrs = tagAttrs[QUOTE] as? QuoteAttributes ?: QuoteAttributes().also {
        tagAttrs[QUOTE] = it
    }
    attrs.body()
}

inline fun StyleSheet.clickable(body: Attributes.() -> Unit) {
    val attrs = tagAttrs[CLICKABLE] ?: Attributes().also {
        tagAttrs[CLICKABLE] = it
    }
    attrs.body()
}
