package com.jones.myquiz.ui.screens.teacherdash.viewModel

import com.jones.myquiz.data.model.Quiz
import kotlinx.coroutines.flow.StateFlow

interface TeacherDashViewModel {

    val quiz: StateFlow<List<Quiz>>

    fun getQuiz()
    fun getCurrentUser()
}