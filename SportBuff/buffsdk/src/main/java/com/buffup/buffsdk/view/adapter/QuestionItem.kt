package com.buffup.buffsdk.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.utils.BuffItem
import com.buffup.buffsdk.utils.QUESTION_ITEM
import com.buffup.sdk.R
import kotlinx.android.synthetic.main.buff_question.view.*

class QuestionItem(
    private val buffViewData: BuffViewData,
    private val timerFinishAction: () -> Unit
) : BuffItem(
    mViewType = QUESTION_ITEM,
    layout = R.layout.buff_question
) {
    override fun bindData(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.questionText.text = buffViewData.question.title
        holder.itemView.circularCountDownProgress.seconds = buffViewData.timeToShow
        holder.itemView.circularCountDownProgress.onFinishedAction = timerFinishAction
        holder.itemView.circularCountDownProgress.start()
    }
}
