package com.jones.myquiz.ui.screens.quiz.viewModel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jones.myquiz.core.service.AuthService
import com.jones.myquiz.data.model.Quiz
import com.jones.myquiz.data.model.Score
import com.jones.myquiz.data.model.User
import com.jones.myquiz.data.repo.QuizRepo
import com.jones.myquiz.data.repo.ScoreRepo
import com.jones.myquiz.data.repo.UserRepo
import com.jones.myquiz.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizViewModelImpl @Inject constructor(
    private val quizRepo: QuizRepo,
    private val scoreRepo: ScoreRepo,
    private val authService: AuthService,
    private val userRepo: UserRepo
) : BaseViewModel(), QuizViewModel {

    private val _quiz: MutableStateFlow<Quiz> = MutableStateFlow(
        Quiz(
            questionTitles = emptyList(),
            options = emptyList(),
            answers = emptyList(),
            timeLimit = -1,
            timeCreated = 0
        )
    )
    val quiz: StateFlow<Quiz> = _quiz

    private val _user =
        MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user

    private val _remainingTime = MutableStateFlow<String?>(null)
    val remainingTime: StateFlow<String?> = _remainingTime

    private val _timerExpired = MutableStateFlow(false)
    val timerExpired: StateFlow<Boolean> = _timerExpired

    init {
        getCurrentUser()
    }

    override fun getQuiz(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                quizRepo.getQuizById(id)
            }?.let {
                Log.d("debugging", "$it")
                _quiz.value = it
                withContext(Dispatchers.Main) {
                    startCountdownTimer(it.timeLimit)
                }
            }
        }
    }

    override fun startCountdownTimer(timeLimit: Long) {
        val countdownTimer = object : CountDownTimer(timeLimit * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val totalSeconds = millisUntilFinished / 1000
                val hours = totalSeconds / 3600
                val minutes = (totalSeconds % 3600) / 60
                val seconds = totalSeconds % 60
                val timeRemaining = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                _remainingTime.value = timeRemaining
            }

            override fun onFinish() {
                _remainingTime.value = "00:00"
                _timerExpired.value = true
            }
        }
        countdownTimer.start()
    }

    override fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        Log.d("debugging", firebaseUser?.uid.toString())
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler { userRepo.getUser(it.uid) }?.let { user ->
                    Log.d("debugging", user.toString())
                    _user.value = user
                }
            }
        }
    }

    fun addResult(result: String, quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                scoreRepo.addResult(
                    Score(result = result, name = user.value.name, quizId = quizId)
                )
            }
        }
    }
}
