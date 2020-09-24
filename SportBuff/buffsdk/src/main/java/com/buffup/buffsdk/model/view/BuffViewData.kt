package com.buffup.buffsdk.model.view

import com.buffup.buffsdk.model.response.Answer
import com.buffup.buffsdk.model.response.Author
import com.buffup.buffsdk.model.response.Question

data class BuffViewData(
    val answers: List<Answer>,
    val author: Author,
    val id: Int,
    val question: Question,
    val time_to_show: Int
)