/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dokar.ktspans.spans

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * Span that updates the typeface of the text it's attached to. The `TypefaceSpan` can
 * be constructed either based on a font family or based on a `Typeface`. When
 * [.TypefaceSpan] is used, the previous style of the `TextView` is kept.
 * When [.TypefaceSpan] is used, the `Typeface` style replaces the
 * `TextView`'s style.
 *
 *
 * For example, let's consider a `TextView` with
 * `android:textStyle="italic"` and a typeface created based on a font from resources,
 * with a bold style. When applying a `TypefaceSpan` based the typeface, the text will
 * only keep the bold style, overriding the `TextView`'s textStyle. When applying a
 * `TypefaceSpan` based on a font family: "monospace", the resulted text will keep the
 * italic style.
 * <pre>
 * Typeface myTypeface = Typeface.create(ResourcesCompat.getFont(context, R.font.acme),
 * Typeface.BOLD);
 * SpannableString string = new SpannableString("Text with typeface span.");
 * string.setSpan(new TypefaceSpan(myTypeface), 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 * string.setSpan(new TypefaceSpan("monospace"), 19, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
</pre> *
 * <img src="{@docRoot}reference/android/images/text/style/typefacespan.png"></img>
 * <figcaption>Text with `TypefaceSpan`s constructed based on a font from resource and
 * from a font family.</figcaption>
 */
class TypefaceSpan private constructor(
    /**
     * Returns the font family name set in the span.
     *
     * @return the font family name
     * @see .TypefaceSpan
     */
    private val family: String?,
    /**
     * Returns the typeface set in the span.
     *
     * @return the typeface set
     * @see .TypefaceSpan
     */
    private val typeface: Typeface?
) :
    MetricAffectingSpan() {

    /**
     * Constructs a [android.text.style.TypefaceSpan] based on the font family. The previous style of the
     * TextPaint is kept. If the font family is null, the text paint is not modified.
     *
     * @param family The font family for this typeface.  Examples include
     * "monospace", "serif", and "sans-serif"
     */
    constructor(family: String?) : this(family, null) {}

    /**
     * Constructs a [android.text.style.TypefaceSpan] from a [Typeface]. The previous style of the
     * TextPaint is overridden and the style of the typeface is used.
     *
     * @param typeface the typeface
     */
    constructor(typeface: Typeface) : this(null, typeface) {}

    override fun updateDrawState(ds: TextPaint) {
        updateTypeface(ds)
    }

    override fun updateMeasureState(paint: TextPaint) {
        updateTypeface(paint)
    }

    private fun updateTypeface(paint: Paint) {
        if (typeface != null) {
            paint.typeface = typeface
        } else if (family != null) {
            applyFontFamily(paint, family)
        }
    }

    private fun applyFontFamily(paint: Paint, family: String) {
        val style: Int
        val old = paint.typeface
        style = old?.style ?: Typeface.NORMAL
        val styledTypeface = Typeface.create(family, style)
        val fake = style and styledTypeface.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }
        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = styledTypeface
    }
}

object TypefaceSpanCompat {

    fun create(typeface: Typeface): Any {
        return if (Build.VERSION.SDK_INT >= 28) {
            android.text.style.TypefaceSpan(typeface)
        } else {
            TypefaceSpan(typeface)
        }
    }

    fun create(family: String?): Any {
        return android.text.style.TypefaceSpan(family)
    }
}