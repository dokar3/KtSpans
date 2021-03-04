package com.dokar.ktspans

import android.content.res.Resources
import android.graphics.Typeface
import android.text.Layout
import android.text.style.DynamicDrawableSpan
import com.dokar.ktspans.spans.BulletSpan
import com.dokar.ktspans.spans.QuoteSpan

class HrAttributes(var height: Px = 1.dp) : Attributes()

class ImgAttributes(
    var width: Int = -1,
    var height: Int = -1,
    var verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM
) : Attributes()

class BulletAttributes(
    var gapWidth: Px = Px(BulletSpan.STANDARD_GAP_WIDTH),
    var bulletColor: Int = BulletSpan.STANDARD_COLOR,
    var bulletRadius: Px = Px(BulletSpan.STANDARD_BULLET_RADIUS)
) : Attributes()

class QuoteAttributes(
    var stripeColor: Int = QuoteSpan.STANDARD_COLOR,
    var stripeWidth: Px = Px(QuoteSpan.STANDARD_STRIPE_WIDTH_PX),
    var gapWidth: Px = Px(QuoteSpan.STANDARD_GAP_WIDTH_PX)
) : Attributes()

open class Attributes(
    var fontSize: Size? = null,
    var fontFamily: Typeface? = null,
    var fontStyle: Int = Typeface.NORMAL,
    var lineHeight: Size? = null,
    var align: Layout.Alignment? = null,
    var backgroundColor: Int = 0,
    var color: Int = 0,
    var decoration: Decoration? = null
)

val Int.em: Em
    get() = Em(this.toFloat())

val Float.em: Em
    get() = Em(this)

val Double.em: Em
    get() = Em(this.toFloat())

val Int.px: Px
    get() = Px(this)

val Int.sp: Px
    get() = this.toDouble().sp

val Float.sp: Px
    get() = this.toDouble().sp

val Double.sp: Px
    get() = Px((Resources.getSystem().displayMetrics.scaledDensity * this).toInt())

val Int.dp: Px
    get() = this.toDouble().dp

val Float.dp: Px
    get() = this.toDouble().dp

val Double.dp: Px
    get() = Px((Resources.getSystem().displayMetrics.density * this).toInt())

class Em(value: Float) : Size(value)

class Px(value: Int) : Size(value.toFloat())

abstract class Size(var value: Float)

enum class Decoration {
    Underline,
    Strikethrough
}