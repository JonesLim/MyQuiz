package com.jones.myquiz.ui.screens.joinquiz.viewModel

import androidx.lifecycle.viewModelScope
import com.jones.myquiz.data.model.Quiz
import com.jones.myquiz.data.repo.QuizRepo
import com.jones.myquiz.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinQuizViewModelImpl @Inject constructor(
    private val quizRepo: QuizRepo
) : BaseViewModel(), JoinQuizViewModel {


    private val _quiz: MutableStateFlow<Quiz> = MutableStateFlow(
        Quiz(
            questionTitles = emptyList(),
            options = emptyList(),
            answers = emptyList(),
            timeLimit = 0,
            timeCreated = 0
        )
    )
    val quiz: StateFlow<Quiz> = _quiz

    override fun getQuiz(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                quizRepo.getQuizById(id)
            }?.let {
                _quiz.value = it
            }
        }
    }

}