package com.jones.myquiz.ui.screens.studentdash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jones.myquiz.R
import com.jones.myquiz.databinding.FragmentStudentDashBinding
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.base.viewModel.BaseViewModel
import com.jones.myquiz.ui.screens.studentdash.viewModel.StudentDashViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentDashFragment : BaseFragment<FragmentStudentDashBinding>() {

    override val viewModel: StudentDashViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStudentDashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.btnJoin.setOnClickListener {
            val action = StudentDashFragmentDirections.studentDashToJoinQuiz()
            navController.navigate(action)
        }

    }


    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)

    }



}