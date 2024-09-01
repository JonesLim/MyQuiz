package com.jones.myquiz.ui.screens.quiz.viewModel

interface QuizViewModel {
    fun getQuiz(id: String)
    fun startCountdownTimer(timeLimit: Long)
    fun getCurrentUser()
}
