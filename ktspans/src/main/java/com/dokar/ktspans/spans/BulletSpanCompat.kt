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

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.annotation.Px


/**
 * A span which styles paragraphs as bullet points (respecting layout direction).
 *
 *
 * BulletSpans must be attached from the first character to the last character of a single
 * paragraph, otherwise the bullet point will not be displayed but the first paragraph encountered
 * will have a leading margin.
 *
 *
 * BulletSpans allow configuring the following elements:
 *
 *  * **gap width** - the distance, in pixels, between the bullet point and the paragraph.
 * Default value is 2px.
 *  * **color** - the bullet point color. By default, the bullet point color is 0 - no color,
 * so it uses the TextView's text color.
 *  * **bullet radius** - the radius, in pixels, of the bullet point. Default value is
 * 4px.
 *
 * For example, a BulletSpan using the default values can be constructed like this:
 * <pre>`SpannableString string = new SpannableString("Text with\nBullet point");
 * string.setSpan(new BulletSpan(), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);`</pre>
 * <img src="{@docRoot}reference/android/images/text/style/defaultbulletspan.png"></img>
 * <figcaption>BulletSpan constructed with default values.</figcaption>
 *
 *
 *
 *
 * To construct a BulletSpan with a gap width of 40px, green bullet point and bullet radius of
 * 20px:
 * <pre>`SpannableString string = new SpannableString("Text with\nBullet point");
 * string.setSpan(new BulletSpan(40, color, 20), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);`</pre>
 * <img src="{@docRoot}reference/android/images/text/style/custombulletspan.png"></img>
 * <figcaption>Customized BulletSpan.</figcaption>
 */
class BulletSpan : LeadingMarginSpan {
    /**
     * Get the distance, in pixels, between the bullet point and the paragraph.
     *
     * @return the distance, in pixels, between the bullet point and the paragraph.
     */
    @Px
    val gapWidth: Int

    /**
     * Get the radius, in pixels, of the bullet point.
     *
     * @return the radius, in pixels, of the bullet point.
     */
    @Px
    val bulletRadius: Int

    /**
     * Get the bullet point color.
     *
     * @return the bullet point color
     */
    @ColorInt
    val color: Int
    private val mWantColor: Boolean

    /**
     * Creates a [BulletSpan] with the default values.
     */
    constructor() : this(STANDARD_GAP_WIDTH, STANDARD_COLOR, false, STANDARD_BULLET_RADIUS) {}

    /**
     * Creates a [BulletSpan] based on a gap width
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     */
    constructor(gapWidth: Int) : this(gapWidth, STANDARD_COLOR, false, STANDARD_BULLET_RADIUS) {}

    /**
     * Creates a [BulletSpan] based on a gap width and a color integer.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     * @param color    the bullet point color, as a color integer
     * @see android.content.res.Resources.getColor
     */
    constructor(gapWidth: Int, @ColorInt color: Int) : this(
        gapWidth,
        color,
        true,
        STANDARD_BULLET_RADIUS
    ) {
    }

    /**
     * Creates a [BulletSpan] based on a gap width and a color integer.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param color        the bullet point color, as a color integer.
     * @param bulletRadius the radius of the bullet point, in pixels.
     * @see android.content.res.Resources.getColor
     */
    constructor(gapWidth: Int, @ColorInt color: Int, @IntRange(from = 0) bulletRadius: Int) : this(
        gapWidth,
        color,
        true,
        bulletRadius
    )

    private constructor(
        gapWidth: Int, @ColorInt color: Int, wantColor: Boolean,
        @IntRange(from = 0) bulletRadius: Int
    ) {
        this.gapWidth = gapWidth
        this.bulletRadius = bulletRadius
        this.color = color
        mWantColor = wantColor
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return 2 * bulletRadius + gapWidth
    }

    override fun drawLeadingMargin(
        canvas: Canvas, paint: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int,
        text: CharSequence, start: Int, end: Int,
        first: Boolean, layout: Layout?
    ) {
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = paint.style
            var oldcolor = 0
            if (mWantColor) {
                oldcolor = paint.color
                paint.color = color
            }
            paint.style = Paint.Style.FILL
            if (layout != null) {
                // "bottom" position might include extra space as a result of line spacing
                // configuration. Subtract extra space in order to show bullet in the vertical
                // center of characters.
                val line = layout.getLineForOffset(start)
                // bottom = bottom - layout.getLineExtra(line);
            }
            val yPosition = (top + bottom) / 2f
            val xPosition = (x + dir * bulletRadius).toFloat()
            canvas.drawCircle(xPosition, yPosition, bulletRadius.toFloat(), paint)
            if (mWantColor) {
                paint.color = oldcolor
            }
            paint.style = style
        }
    }

    companion object {
        // Bullet is slightly bigger to avoid aliasing artifacts on mdpi devices.
        const val STANDARD_BULLET_RADIUS = 4
        const val STANDARD_GAP_WIDTH = 2
        const val STANDARD_COLOR = 0
    }
}


object BulletSpanCompat {

    fun create(gapWidth: Int, color: Int, bulletRadius: Int): Any {
        return if (Build.VERSION.SDK_INT >= 28) {
            android.text.style.BulletSpan(gapWidth, color, bulletRadius)
        } else {
            BulletSpan(gapWidth, color, bulletRadius)
        }
    }
}