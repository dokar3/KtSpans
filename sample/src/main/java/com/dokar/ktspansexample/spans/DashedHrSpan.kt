package com.dokar.ktspansexample.spans

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.text.style.ReplacementSpan
import com.dokar.ktspans.dp

class DashedHrSpan(
    private val height: Float = 1.dp.value,
    private val color: Int = TEXT_COLOR,
) : ReplacementSpan() {

    private val dashPathEffect = DashPathEffect(
        floatArrayOf(
            16.dp.value,
            8.dp.value
        ),
        0f
    )

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ) = 0

    override fun draw(
        canvas: Canvas, text: CharSequence?, start: Int, end: Int,
        x: Float, top: Int, y: Int, bottom: Int, paint: Paint
    ) {
        paint.style = Paint.Style.STROKE
        paint.pathEffect = dashPathEffect
        paint.strokeWidth = height
        if (color != TEXT_COLOR) {
            paint.color = color
        }
        val middle = ((top + bottom) / 2).toFloat()
        canvas.drawLine(0f, middle, canvas.width.toFloat(), middle, paint)
    }

    companion object {
        const val TEXT_COLOR = Color.TRANSPARENT
    }
}