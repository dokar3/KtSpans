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

import android.graphics.Paint.FontMetricsInt
import android.text.style.LineHeightSpan
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.Px

/**
 * Default implementation of the [LineHeightSpan], which changes the line height of the
 * attached paragraph.
 *
 *
 * For example, a paragraph with its line height equal to 100px can be set like this:
 * <pre>
 * SpannableString string = new SpannableString("This is a multiline paragraph. This is a multiline paragraph.");
 * string.setSpan(new LineHeightSpan.Standard(100), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
</pre> *
 * <img src="{@docRoot}reference/android/images/text/style/lineheightspan.png"></img>
 * <figcaption>Text with line height set to 100 pixels.</figcaption>
 *
 *
 * Notice that LineHeightSpan will change the line height of the entire paragraph, even though it
 * covers only part of the paragraph.
 *
 */
class StdLineHeightSpan : LineHeightSpan {
    /**
     * Returns the line height specified by this span.
     */
    @Px
    var height = 0
        private set
    private var mMultiple = 1f

    /**
     * Set the line height of the paragraph to `height` physical pixels.
     */
    constructor(@Px @IntRange(from = 1) height: Int) {
        this.height = height
    }

    constructor(@FloatRange(from = 0.0) multiple: Float) {
        mMultiple = multiple
    }

    override fun chooseHeight(
        text: CharSequence, start: Int, end: Int,
        spanstartv: Int, lineHeight: Int,
        fm: FontMetricsInt
    ) {
        val originHeight = fm.descent - fm.ascent
        // If original height is not positive, do nothing.
        if (originHeight <= 0) {
            return
        }
        val newHeight: Int = if (mMultiple != 1f) {
            (originHeight * mMultiple).toInt()
        } else if (height != 0) {
            height
        } else {
            return
        }
        val ratio = newHeight * 1.0f / originHeight
        fm.descent = Math.round(fm.descent * ratio)
        fm.ascent = fm.descent - newHeight
    }

}

