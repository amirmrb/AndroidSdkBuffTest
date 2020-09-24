package com.buffup.buffupSdk.view.custom

import android.view.animation.Animation
import android.view.animation.Transformation
import java.lang.IllegalArgumentException

class ProgressBarAnimation(
    private val progressBar: CircleProgressBar,
    private val start: Float,
    private val end: Float
) : Animation() {
    init {
        progressBar.max = 100
        if (start !in 0f..1f) throw IllegalArgumentException("`start` should be between 0f, 1f")
        if (end !in 0f..1f) throw IllegalArgumentException("`end` should be between 0f, 1f")
        if (start > end)  throw IllegalArgumentException("`start` should be lower than `end`")
    }
    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)
        val value = start + (end - start) * interpolatedTime
        progressBar.progress = (value * 100).toInt()
    }
}