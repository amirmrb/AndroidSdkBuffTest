package com.buffup.buffsdk.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolderCreator {
    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
}
