package com.buffup.buffupSdk.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.widget.ProgressBar
import com.buffup.buffupSdk.view.custom.ProgressBarAnimation

class CircleProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ProgressBar(context, attrs, defStyleAttr, defStyleRes) {
    private var duration = 0L
    private val progressBarAnimation: ProgressBarAnimation
    private val animSet = AnimationSet(context, attrs)

    init {
        max = 100
        val verticalAnimation = RotateAnimation(
            0f,
            -90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        progressBarAnimation =
            ProgressBarAnimation(this, 0f, 1f)
        animSet.addAnimation(verticalAnimation)
    }

    fun start() {
        if (duration <= 1000L) throw IllegalArgumentException("duration must be set and grater than 1000 milliseconds !")
        progressBarAnimation.duration = duration
        animSet.addAnimation(progressBarAnimation)
        this.startAnimation(animSet)
    }

    fun stop() {
        progressBarAnimation.cancel()
    }

    fun setTime(seconds: Int) {
        duration = seconds * 1000L - 1000L // to show smooth animation
    }
}