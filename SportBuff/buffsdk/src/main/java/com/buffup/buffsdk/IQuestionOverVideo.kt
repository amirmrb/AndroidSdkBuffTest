package com.buffup.buffsdk

interface IQuestionOverVideo<T> {
    var status: Status
    fun initialize()
    fun loadNextQuestion()
    fun onQuestionTimeFinished()
    fun getRemainingTime(): Long
    fun submitAnswer(answer: T)
    fun close()
}


sealed class Status

object FetchData : Status()
object Showing : Status()
object SubmittedAnswerStatus : Status()
object IdealStatus : Status()