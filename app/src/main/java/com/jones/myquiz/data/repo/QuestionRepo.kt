package com.jones.myquiz.data.repo

import com.jones.myquiz.data.model.Question

interface QuestionRepo {

    suspend fun getQuestionsByQuizId(quizId: String): Question?
}