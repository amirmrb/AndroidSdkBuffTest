package com.buffup.buffsdk.model.view

import com.buffup.buffsdk.model.response.Question

data class BuffViewData(
    val answers: List<AnswerData>,
    val author: AuthorData,
    val id: Int,
    val question: Question,
    val timeToShow: Int
)