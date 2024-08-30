package com.jones.myquiz.ui.screens.joinquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import com.jones.myquiz.R
import com.jones.myquiz.databinding.FragmentJoinQuizBinding
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.joinquiz.viewModel.JoinQuizViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoinQuizFragment : BaseFragment<FragmentJoinQuizBinding>() {

    override val viewModel: JoinQuizViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentJoinQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.run {
            btnStart.setOnClickListener {
                val id = etQuizId.text.toString().trim()  // Trim any leading or trailing whitespace


                if (id.isEmpty()) {
                    showSnackbar(view, "Please enter a valid ID!", isError = true)
                } else {
                    checkQuizId(id)
                }
            }

        }


        binding.btnBack.setOnClickListener {
            navController.popBackStack()
            Log.d("debugging", "setupUiComponents: ")
        }
    }


    private fun checkQuizId(id: String) {
        lifecycleScope.launch {
            // Fetch the quiz data and ensure it completes before proceeding
            viewModel.getQuiz(id)

            // Collect the latest quiz data from the Flow
            viewModel.quiz.firstOrNull { quiz ->
                quiz != null && quiz.id == id
            }?.let { quiz ->
                // ID exists, proceed to the quiz
                val action = JoinQuizFragmentDirections.joinQuizToQuiz(id)
                navController.navigate(action)
            } ?: run {
                // ID doesn't exist
                showSnackbar(requireView(), "ID doesn't exist!", isError = true)
            }
        }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.quiz.collect {
                Log.d("debugging", it.toString())
            }
        }
    }

//    private fun showErrorMessage(message: String) {
//        Log.d("debugging", "Error: $message")
//    }

    private fun showErrorMessageJoinQuiz(message: String) {
        showSnackbar(requireView(), message, isError = true)
    }

}