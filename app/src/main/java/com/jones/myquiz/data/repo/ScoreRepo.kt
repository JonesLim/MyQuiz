package com.jones.myquiz.data.repo

import com.jones.myquiz.data.model.Score
import kotlinx.coroutines.flow.Flow

interface ScoreRepo {
    suspend fun addResult(score: Score)
    suspend fun getResult() : Flow<List<Score>>
}