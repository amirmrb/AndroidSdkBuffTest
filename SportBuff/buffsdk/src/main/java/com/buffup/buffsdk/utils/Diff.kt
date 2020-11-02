package com.buffup.buffsdk.utils


import androidx.recyclerview.widget.DiffUtil
import com.buffup.buffsdk.view.adapter.AnswerItem

class BuffDiffUtil(var oldList: List<BuffItem>, var newList: List<BuffItem>) :
    DiffUtil.Callback() {


    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return false
        if (oldList[oldPos] is AnswerItem && newList[newPos] is AnswerItem)
            return (oldList[oldPos] as AnswerItem).getData() == (newList[newPos] as AnswerItem).getData()
        return false
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        return false
        return when {
            oldList[oldPos] is AnswerItem && newList[newPos] is AnswerItem -> (oldList[oldPos] as AnswerItem).getData() == (newList[newPos] as AnswerItem).getData()
            else -> false
        }
    }

}