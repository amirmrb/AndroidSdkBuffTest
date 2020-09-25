package com.buffup.buffsdk.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.repo.BuffRepository
import com.buffup.buffsdk.utils.*
import com.buffup.buffsdk.view.adapter.AnswerItem
import com.buffup.buffsdk.view.adapter.QuestionItem
import com.buffup.buffsdk.view.adapter.SenderItem
import com.buffup.buffsdk.viewModel.BuffViewModel
import com.buffup.sdk.R
import kotlinx.android.synthetic.main.buff_view.view.*

class BuffView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    lateinit var videoView: VideoView

    private val viewModel: BuffViewModel
    private lateinit var buffView: View
    private val adapter = BaseItemAdapter()

    init {
        if (context !is AppCompatActivity) throw IllegalArgumentException("context must be AppCompatActivity")
        SharedTexts.context = context
        ConnectivityChecker.appContext = context
        val activity = this.context as AppCompatActivity
        viewModel = BuffViewModel(BuffRepository())
        viewModel.initialize()

        viewModel.buffViewData.observe(activity, Observer {
            showQuestion(it)
        })
        viewModel.hideBuffViewData.observe(activity, Observer {
            it?.let {
                hideQuestion()
                viewModel.hideBuffViewData.clear()
            }
        })
        viewModel.answerSelectedLiveData.observe(activity, Observer { answer ->
            answer?.let {
                val answerItem = adapter.adapterData[answer.position] as AnswerItem
                answerItem.getAnswerData().isSelected = true
                adapter.adapterData.removeAt(answer.position)
                adapter.adapterData.add(answer.position, answerItem)
                adapter.notifyItemChanged(answer.position)
            }
        })
    }

    private fun showQuestion(bvd: BuffViewData) {
        if (::buffView.isInitialized) // fixme
        {
            val animation = AnimationUtils.loadAnimation(context, R.anim.entry_anim)
            buffView.startAnimation(animation)
            buffView.visibility = View.VISIBLE
        }
        adapter.clear()
        adapter.adapterData.add(SenderItem(bvd, viewModel::close))
        adapter.adapterData.add(QuestionItem(bvd, viewModel::onQuestionTimeFinished))
        bvd.answers.forEach { adapter.adapterData.add(AnswerItem(it, viewModel::submitAnswer)) }
        adapter.notifyDataSetChanged()
    }

    private fun hideQuestion() {
        if (::buffView.isInitialized) // fixme
        {
            buffView.visibility = View.GONE
            val animation = AnimationUtils.loadAnimation(context, R.anim.exit_anim)
            buffView.startAnimation(animation)
        }
    }


    fun show() {
        val videoPlayerParent = videoView.parent as ViewGroup
        buffView =
            LayoutInflater.from(context).inflate(R.layout.buff_view, videoPlayerParent, false)
        val params = LayoutParams(
            resources.getDimensionPixelSize(R.dimen.buff_size_10),
            LayoutParams.WRAP_CONTENT
        )
        if (videoPlayerParent is FrameLayout) {
            params.gravity = Gravity.BOTTOM
            videoPlayerParent.addView(buffView, params)
            videoPlayerParent.requestLayout()
            videoPlayerParent.invalidate()
        } else if (videoPlayerParent is ConstraintLayout) {
            TODO("not implemented yet !")
        }
        buffView.recycler.layoutManager = LinearLayoutManager(context)
        buffView.recycler.adapter = adapter
    }
}