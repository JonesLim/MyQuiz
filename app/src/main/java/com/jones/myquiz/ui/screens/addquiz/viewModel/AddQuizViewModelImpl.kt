package com.jones.myquiz.ui.screens.addquiz.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jones.myquiz.core.service.AuthService
import com.jones.myquiz.data.model.Question
import com.jones.myquiz.data.model.Quiz
import com.jones.myquiz.data.model.User
import com.jones.myquiz.data.repo.QuizRepo
import com.jones.myquiz.data.repo.UserRepo
import com.jones.myquiz.ui.screens.addquiz.viewModel.AddQuizViewModel
import com.jones.myquiz.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizViewModelImpl @Inject constructor(
    private val authService: AuthService,
    private val quizRepo: QuizRepo,
    private val userRepo: UserRepo
) : BaseViewModel(), AddQuizViewModel {

    private val _finish = MutableSharedFlow<Unit>()
    override val finish: SharedFlow<Unit> = _finish

    private val _quizQuestion = MutableStateFlow<List<Question>>(emptyList())
    val quizQuestion: StateFlow<List<Question>> = _quizQuestion

    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user

    private val _timer = MutableStateFlow<Long?>(null)
    val timer: StateFlow<Long?> = _timer

    init {
        getCurrentUser()
    }

    override fun addQuiz(quizId: String, title: String, timer: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            val questionTitles = mutableListOf<String>()
            val options = mutableListOf<String>()
            val answers = mutableListOf<String>()
            _quizQuestion.value.map {
                questionTitles.add(it.question)
                options.add(it.option1)
                options.add(it.option2)
                options.add(it.option3)
                options.add(it.option4)
                answers.add(it.correctAnswer)
            }
            val currentTimeMillis = System.currentTimeMillis()
            errorHandler {
                quizRepo.addQuiz(
                    Quiz(
                        QuizId = quizId,
                        title = title,
                        questionTitles = questionTitles,
                        options = options,
                        answers = answers,
                        creatorName = user.value.name,
                        timeLimit = timer ?: 0,
                        timeCreated = currentTimeMillis
                    )
                )
            }
            _finish.emit(Unit)
        }
    }

    fun getCurrentUser() {
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

    fun checkQuizIdExists(quizId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = quizRepo.getQuizById(quizId) != null
            callback(exists)
        }
    }

    override fun readCsv(lines: List<String>) {
        viewModelScope.launch {
            try {
                val questions = mutableListOf<Question>()
                var timer: Long? = null
                lines.forEachIndexed { index, line ->
                    if (index == lines.size - 1) {
                        // Last line should be the timer
                        timer = line.toLongOrNull()
                    } else {
                        val title = line.split(",")
                        if (title.size >= 6) {
                            questions.add(
                                Question(
                                    question = title[0],
                                    option1 = title[1],
                                    option2 = title[2],
                                    option3 = title[3],
                                    option4 = title[4],
                                    correctAnswer = title[5]
                                )
                            )
                        }
                    }
                }
                _quizQuestion.emit(questions)
                _timer.emit(timer)
                _success.emit("CSV Import Successful")
                Log.d("debugging", "CSV Import Successful: ${questions.size} questions imported.")
            } catch (e: Exception) {
                Log.e("debugging", "Error parsing CSV file: ${e.message}")
            }
        }
    }
}
