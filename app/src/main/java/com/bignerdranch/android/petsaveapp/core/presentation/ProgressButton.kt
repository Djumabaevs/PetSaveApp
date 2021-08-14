package com.bignerdranch.android.petsaveapp.core.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.bignerdranch.android.petsaveapp.R

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.progressButtonStyle,
    defStyleRes: Int = R.style.ProgressButtonStyle
): View(context, attrs, defStyleAttr, defStyleRes) {

    private var buttonText = ""

    private val textPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        textSize = context.dpToPx(16f)
    }

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = context.dpToPx(2f)
    }

    private val buttonRect = RectF()
    private val progressRect = RectF()

    private var buttonRadius = context.dpToPx(16f)

    private var offset: Float = 0f

    private var widthAnimator: ValueAnimator? = null
    private var loading = false
    private var startAngle = 0f

    private var rotationAnimator: ValueAnimator? = null
    private var drawTick = false
}