package com.buffup.buffsdk.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val adapterData: CustomList<BuffItem> = CustomList {
        adapterMap[it.mViewType] = it.viewHolderCreator
    }
    private val adapterMap: HashMap<Int, ViewHolderCreator> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return (adapterMap[viewType]?.onCreateViewHolder(
            parent,
            viewType
        ) ?: error("$viewType is not defined !"))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapterData[position].bindData(holder, position)

    override fun getItemCount() = adapterData.size
    override fun getItemViewType(position: Int): Int = adapterData[position].mViewType

    fun clear() {
        adapterData.clear()
    }
}
