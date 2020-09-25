package com.buffup.buffsdk.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffup.buffsdk.utils.ViewHolderCreator


abstract class BuffItem(
    val mViewType: Int,
    val layout: Int
) {
    abstract fun bindData(
        holder: RecyclerView.ViewHolder,
        position: Int
    )
    open fun onClickListener(): View.OnClickListener? = null

    val viewHolderCreator by lazy {
        object : ViewHolderCreator() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(layout, parent, false)
                itemView.setOnClickListener(onClickListener())
                return onViewHolderCreated(object : RecyclerView.ViewHolder(
                    itemView
                ) {})
            }
        }
    }

    open fun onViewHolderCreated(viewHolder: RecyclerView.ViewHolder): RecyclerView.ViewHolder {
        //TODO : if any shared functionality on all view holders put it here
        return viewHolder
    }
}