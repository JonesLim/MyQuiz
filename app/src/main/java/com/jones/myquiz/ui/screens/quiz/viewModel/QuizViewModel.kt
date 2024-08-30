package com.jones.myquiz.ui.screens.quiz.viewModel

import com.jones.myquiz.data.model.Quiz
import com.jones.myquiz.data.model.User
import kotlinx.coroutines.flow.StateFlow

interface QuizViewModel {
    fun getQuiz(id: String)
    fun startCountdownTimer(timeLimit: Long)
    fun getCurrentUser()
}

//    fun getQuizById(id: String)
//    fun addResult(result: String, quizId: String)
