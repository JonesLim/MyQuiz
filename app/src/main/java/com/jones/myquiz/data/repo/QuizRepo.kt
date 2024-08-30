package com.jones.myquiz.data.repo

import com.jones.myquiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepo {

    suspend fun getAllQuiz(): Flow<List<Quiz>>

    suspend fun addQuiz(quiz: Quiz)

    suspend fun getQuizById(id: String): Quiz?

}