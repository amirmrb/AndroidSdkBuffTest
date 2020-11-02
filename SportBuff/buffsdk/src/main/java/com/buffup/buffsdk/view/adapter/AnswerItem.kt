package com.buffup.buffsdk.view.adapter

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.buffup.buffsdk.model.view.AnswerData
import com.buffup.buffsdk.utils.ANSWER_ITEM
import com.buffup.buffsdk.utils.BuffItem
import com.buffup.sdk.R
import kotlinx.android.synthetic.main.buff_answer.view.*

class AnswerItem(
    private val answerViewData: AnswerData,
    private val onClickAction: (AnswerData) -> Unit
) :
    BuffItem(
        mViewType = ANSWER_ITEM,
        layout = R.layout.buff_answer
    ), RecyclerItem<AnswerData> {

    override fun onViewHolderCreated(viewHolder: RecyclerView.ViewHolder): RecyclerView.ViewHolder {
        viewHolder.itemView.answerContainer.addTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                viewHolder.itemView.answerContainer.transitionToState(R.id.answerImageTransition)
                onClickAction.invoke(viewHolder.itemView.tag as AnswerData)
            }
        })
        return super.onViewHolderCreated(viewHolder)
    }

    override fun bindData(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.answerText.text = answerViewData.answer.title
        if (answerViewData.isSelected) {
            holder.itemView.answerText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.test_color_light)
            )
        } else {
            holder.itemView.answerText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.test_color_dark)
            )
        }
        answerViewData.position = position
        holder.itemView.tag = answerViewData
    }

    override fun getData() = answerViewData
}
