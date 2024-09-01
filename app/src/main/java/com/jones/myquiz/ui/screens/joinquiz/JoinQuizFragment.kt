package com.jones.myquiz.ui.screens.joinquiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jones.myquiz.databinding.FragmentJoinQuizBinding
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.joinquiz.viewModel.JoinQuizViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
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
                val id = etQuizId.text.toString().trim()

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
            viewModel.getQuiz(id)

            viewModel.quiz.firstOrNull { quiz ->
                quiz.id == id
            }?.let { quiz ->
                val action = JoinQuizFragmentDirections.joinQuizToQuiz(id)
                navController.navigate(action)
            } ?: run {
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

}