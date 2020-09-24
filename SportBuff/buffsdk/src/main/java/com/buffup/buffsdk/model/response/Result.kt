package com.buffup.buffsdk.model.response

data class Result(
    val answers: List<Answer>,
    val author: Author,
    val clientId: Int,
    val createdAt: String,
    val id: Int,
    val language: String,
    val priority: Int,
    val question: Question,
    val streamId: Int,
    val timeToShow: Int
)