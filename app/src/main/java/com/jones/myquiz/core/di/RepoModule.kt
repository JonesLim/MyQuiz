package com.jones.myquiz.core.di

import com.jones.myquiz.core.service.AuthService
//import com.jones.myquiz.data.repo.QuestionRepo
//import com.jones.myquiz.data.repo.QuestionRepoImpl
//import com.jones.myquiz.data.repo.QuizRepo
//import com.jones.myquiz.data.repo.QuizRepoImpl
//import com.jones.myquiz.data.repo.ScoreRepo
//import com.jones.myquiz.data.repo.ScoreRepoImpl
import com.jones.myquiz.data.repo.UserRepo
import com.jones.myquiz.data.repo.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    @Singleton
    fun providesUserRepo(authService: AuthService): UserRepo {
        return UserRepoImpl(authService = authService)
    }

//    @Provides
//    @Singleton
//    fun providesQuizRepo(authService: AuthService): QuizRepo {
//        return QuizRepoImpl(authService = authService)
//    }
//
//    @Provides
//    @Singleton
//    fun providesQuestionRepo(authService: AuthService): QuestionRepo {
//        return QuestionRepoImpl(authService = authService)
//    }
//
//    @Provides
//    @Singleton
//    fun providesScoreRepo(authService: AuthService): ScoreRepo {
//        return ScoreRepoImpl(authService = authService)
//    }

}