package com.expeditee.plantdiagnosis.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.expeditee.plantdiagnosis.R
import kotlin.math.min

class PlantFocusOverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val overlayPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val framePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val overlayColor = ContextCompat.getColor(context, R.color.overlay_light)
    private val frameColor = ContextCompat.getColor(context, R.color.accent_green)

    private var frameWidth = 4f
    private var cornerRadius = 16f
    private var boxFraction = 0.90f

    private val frameRect = RectF()

    init {
        overlayPaint.color = overlayColor
        framePaint.color = frameColor
        framePaint.style = Paint.Style.STROKE
        framePaint.strokeWidth = frameWidth
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateFrameRect()
    }

    private fun updateFrameRect() {
        val size = min(width, height) * boxFraction
        val left = (width - size) / 2f
        val top = (height - size) / 3.5f
        frameRect.set(left, top, left + size, top + size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(overlayColor)
        canvas.drawRoundRect(frameRect, cornerRadius, cornerRadius, clearPaint)
        canvas.drawRoundRect(frameRect, cornerRadius, cornerRadius, framePaint)
    }

    fun setBoxFraction(fraction: Float) {
        boxFraction = fraction.coerceIn(0.2f, 0.9f)
        updateFrameRect()
        invalidate()
    }

    fun getScanArea(): RectF {
        return RectF(frameRect)
    }

    fun getScanAreaRatio(): Pair<Float, Float> {
        val widthRatio = frameRect.width() / width
        val heightRatio = frameRect.height() / height
        return Pair(widthRatio, heightRatio)
    }

    fun getScanAreaNormalized(): RectF = RectF(
        frameRect.left / width,
        frameRect.top / height,
        frameRect.right / width,
        frameRect.bottom / height
    )

}
