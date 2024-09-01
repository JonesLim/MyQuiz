package com.jones.myquiz.ui.screens.studentdash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jones.myquiz.databinding.FragmentStudentDashBinding
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.studentdash.viewModel.StudentDashViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentDashFragment : BaseFragment<FragmentStudentDashBinding>() {

    override val viewModel: StudentDashViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
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

}