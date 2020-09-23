package com.buffup.buffsdk.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.VideoView
import com.buffup.buffsdk.IQuestionOverVideo
import com.buffup.buffsdk.IdealStatus
import com.buffup.buffsdk.Status
import com.buffup.buffsdk.model.response.Answer

class QuestionOverVideo @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr),
    IQuestionOverVideo<Answer> {

    lateinit var videoView: VideoView

    override var status: Status = IdealStatus

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun getRemainingTime(): Long {
        TODO("Not yet implemented")
    }

    override fun submitAnswer(answer: Answer) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}