package com.buffup.buffsdk.view.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.buffup.buffsdk.model.response.Answer
import com.buffup.buffsdk.model.view.AnswerData
import com.buffup.buffsdk.utils.ANSWER_ITEM
import com.buffup.buffsdk.utils.BuffItem
import com.buffup.sdk.R
import kotlinx.android.synthetic.main.buff_answer.view.*

class AnswerItem(private val answerViewData: AnswerData, private val onClickAction: (AnswerData) -> Unit) :
    BuffItem(
        mViewType = ANSWER_ITEM,
        layout = R.layout.buff_answer
    ) {

    override fun onViewHolderCreated(viewHolder: RecyclerView.ViewHolder): RecyclerView.ViewHolder {
        viewHolder.itemView.setOnClickListener { onClickAction.invoke(it.tag as AnswerData) }
        return super.onViewHolderCreated(viewHolder)
    }

    override fun bindData(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.answerText.text = answerViewData.answer.title
        holder.itemView.answerText.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if(answerViewData.isSelected) R.color.test_color_light else R.color.test_color_dark
            )
        )
        answerViewData.position = position
        holder.itemView.tag = answerViewData
    }

    fun getAnswerData() = answerViewData
}
