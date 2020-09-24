package com.buffup.buffsdk.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.buffup.sdk.R
import kotlinx.android.synthetic.main.count_down_timer.view.*


class CountDownCircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    lateinit var onFinishedAction: () -> Unit

    var seconds: Int = 0
    var isCounting: Boolean = false

    private val countDown :
            SecondCountDownTimer by lazy {SecondCountDownTimer(
        seconds,
        ::onFinish,
        ::onTick
    )}

    init {
        val typedArray =  context.obtainStyledAttributes(
            attrs,
            R.styleable.CountDownCircularProgressView,
            defStyleAttr,
            defStyleRes
        )
        if (seconds == 0)
            seconds = typedArray.getInteger(R.styleable.CountDownCircularProgressView_seconds, 0)
        View.inflate(
            context,
            R.layout.count_down_timer, this
        )
    }

    private fun onFinish() {
        if (::onFinishedAction.isInitialized)
            onFinishedAction.invoke()
        isCounting = false
    }

    private fun onTick(secondsUntilFinished: Int) {
        countDownText.text = secondsUntilFinished.toString()
    }

    fun start() {
        if (seconds == 0) throw IllegalArgumentException("seconds should be set before starting ...")
        isCounting = true
        countDown.start()
        circularProgress.setTime(seconds)
        circularProgress.start()
    }

    fun stop() {
        countDown.cancel()
        circularProgress.stop()
        onFinish()
    }
}
