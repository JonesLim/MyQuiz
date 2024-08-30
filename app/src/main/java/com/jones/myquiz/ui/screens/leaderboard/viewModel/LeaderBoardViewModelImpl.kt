package com.jones.myquiz.ui.screens.leaderboard.viewModel

import androidx.lifecycle.viewModelScope
import com.jones.myquiz.data.model.Score
import com.jones.myquiz.data.repo.ScoreRepo
import com.jones.myquiz.ui.screens.base.viewModel.BaseViewModel
import com.jones.myquiz.ui.screens.leaderboard.viewModel.LeaderBoardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModelImpl @Inject constructor(
    private val scoreRepo: ScoreRepo
) : BaseViewModel(), LeaderBoardViewModel {

    private val _score: MutableStateFlow<List<Score>> = MutableStateFlow(emptyList())
    val score: StateFlow<List<Score>> = _score


    init {
        getScore()
    }

    fun getScore() {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                scoreRepo.getResult()
            }?.collect {
                _score.value = it
            }
        }
    }
}