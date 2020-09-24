package com.buffup.buffsdk.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.buffup.buffsdk.model.view.BuffViewData
import com.buffup.buffsdk.utils.BuffItem
import com.buffup.buffsdk.utils.SENDER_ITEM
import com.buffup.sdk.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.buff_sender.view.*

class SenderItem(private val sender: BuffViewData, private val onCloseAction: () -> Unit) : BuffItem(
    mViewType = SENDER_ITEM,
    layout = R.layout.buff_sender
) {
    override fun onViewHolderCreated(viewHolder: RecyclerView.ViewHolder): RecyclerView.ViewHolder {
        viewHolder.itemView.buffClose.setOnClickListener { onCloseAction() }
        return super.onViewHolderCreated(viewHolder)
    }
    override fun bindData(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(sender.author.image)
            .into(holder.itemView.senderImage)
        holder.itemView.senderName.text = sender.author.name
    }
}
