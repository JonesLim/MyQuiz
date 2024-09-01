package com.jones.myquiz.ui.screens.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jones.myquiz.databinding.FragmentLoginBinding
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.login.viewModel.LoginViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel: LoginViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showSnackbar(view, "All fields are required!", isError = true)
            } else {
                viewModel.login(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            val action = LoginFragmentDirections.loginToRegister()
            navController.navigate(action)
        }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.user.collect { user ->
                Log.d("debugging", "User role: ${user.role}")
                val action = when (user.role) {
                    "Student" -> LoginFragmentDirections.loginToStudentDash()
                    "Teacher" -> LoginFragmentDirections.loginToTeacherDash()
                    else -> null
                }
                action?.let { navController.navigate(action) }
            }
        }

        lifecycleScope.launch {
            viewModel.success.collect {
                viewModel.getCurrentUser()
            }
        }
    }
}
