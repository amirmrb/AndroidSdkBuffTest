package com.buffup.buffsdk.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.buffup.buffsdk.model.response.Answer
import com.buffup.buffsdk.utils.ANSWER_ITEM
import com.buffup.buffsdk.utils.BuffItem
import com.buffup.sdk.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.buff_answer.view.*

class AnswerItem(val answer: Answer, val onClickAction: (Answer) -> Unit) : BuffItem(
    mViewType = ANSWER_ITEM,
    layout = R.layout.buff_answer
) {
    override fun onViewHolderCreated(viewHolder: RecyclerView.ViewHolder): RecyclerView.ViewHolder {
        viewHolder.itemView.setOnClickListener { onClickAction.invoke(it.tag as Answer) }
        return super.onViewHolderCreated(viewHolder)
    }
    override fun bindData(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.answerText.text = answer.title
        holder.itemView.tag = answer
    }
}
