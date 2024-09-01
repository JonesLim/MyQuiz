package com.jones.myquiz.ui.screens.teacherdash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jones.myquiz.databinding.FragmentTeacherDashBinding
import com.jones.myquiz.ui.adapter.QuizAdapter
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.teacherdash.viewModel.TeacherDashViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherDashFragment : BaseFragment<FragmentTeacherDashBinding>() {

    override val viewModel: TeacherDashViewModelImpl by viewModels()
    private lateinit var adapter: QuizAdapter
    private var fileName: String = "DefaultFileName"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeacherDashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        setupAdapter()

        binding.btnCreate.setOnClickListener {
            val action = TeacherDashFragmentDirections.teacherDashToAddQuiz()
            navController.navigate(action)
        }

    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.quiz.collect {
                adapter.setQuiz(it)
            }
        }

    }

    private fun setupAdapter() {
        Log.d("FileName", "Value of fileName: $fileName")
        adapter = QuizAdapter(emptyList())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvQuiz.adapter = adapter
        binding.rvQuiz.layoutManager = layoutManager
    }

}