package com.buffup.buffsdk.utils

class CustomList<T>(val onAddCall: (T) -> Unit) : ArrayList<T>() {
    override fun add(element: T): Boolean {
        onAddCall(element)
        return super.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elements.forEach {
            onAddCall(it)
        }
        return super.addAll(elements)
    }

}