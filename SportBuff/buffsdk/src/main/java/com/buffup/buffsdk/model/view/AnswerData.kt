package com.buffup.buffsdk.model.view

import com.buffup.buffsdk.model.response.Answer

data class AnswerData(val answer: Answer, var isSelected: Boolean = false, var position: Int = 0)
