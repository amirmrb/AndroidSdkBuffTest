package com.buffup.buffsdk.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.buffup.buffsdk.IdealStatus
import com.buffup.buffsdk.Status
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import com.buffup.buffsdk.utils.BaseItemAdapter
import com.buffup.buffsdk.utils.ConnectivityChecker
import com.buffup.buffsdk.utils.SharedTexts
import com.buffup.buffsdk.view.adapter.AnswerItem
import com.buffup.buffsdk.view.adapter.QuestionItem
import com.buffup.buffsdk.view.adapter.SenderItem
import com.buffup.buffsdk.viewModel.BuffViewModel
import com.buffup.sdk.R
import kotlinx.android.synthetic.main.buff_view.view.*

class QuestionOverVideo @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    var status: Status = IdealStatus
    lateinit var videoView: VideoView

    private lateinit var viewModel: BuffViewModel
    private lateinit var buffView : View
    private val adapter = BaseItemAdapter()

    init {
        if (context !is AppCompatActivity) throw IllegalArgumentException("context must be AppCompatActivity")
        val activity = this.context as AppCompatActivity
        viewModel = BuffViewModel(BuffRepository())
        viewModel.buffViewData.observe(activity, Observer {
            showQuestion(it)
        })
        viewModel.hideBuffViewData.observe(activity, Observer {
            it?.let { hideQuestion() }
        })
        SharedTexts.context = context
        ConnectivityChecker.appContext = context
    }

    private fun showQuestion(bvd: BuffViewData) {
        adapter.clear()
        adapter.adapterData.add(SenderItem(bvd))
        adapter.adapterData.add(QuestionItem(bvd), viewModel::onQuestionTimeFinished)
        bvd.answers.forEach { adapter.adapterData.add(AnswerItem(it, viewModel::submitAnswer)) }
    }

    private fun hideQuestion() {
        viewModel.close()
    }


    fun show() {
        val videoPlayerParent = videoView.parent as ViewGroup
        val questionView = LayoutInflater.from(context).inflate(R.layout.buff_view, videoPlayerParent, false)
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        if (videoPlayerParent is FrameLayout) {
            params.gravity = Gravity.BOTTOM
            videoPlayerParent.addView(questionView, params)
            videoPlayerParent.requestLayout()
            videoPlayerParent.invalidate()
        }
        else if (videoPlayerParent is ConstraintLayout) {
            TODO("not implemented yet !")
        }
        questionView.recycler.layoutManager = LinearLayoutManager(context)
        questionView.recycler.adapter = adapter
    }

}