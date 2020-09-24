package com.buffup.buffsdk

interface IBuff<T> {
    fun initialize()
    fun loadNextQuestion()
    fun onQuestionTimeFinished()
    fun submitAnswer(answer: T)
    fun close()
}