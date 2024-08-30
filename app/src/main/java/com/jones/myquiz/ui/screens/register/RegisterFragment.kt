package com.jones.myquiz.ui.screens.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jones.myquiz.R
import com.jones.myquiz.databinding.FragmentRegisterBinding
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.register.viewModel.RegisterViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val viewModel: RegisterViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roles = resources.getStringArray(R.array.roles_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roles)

        binding.atvRole.setAdapter(adapter)
        binding.atvRole.setOnClickListener {
            binding.atvRole.showDropDown()
        }

        setupUiComponents(view)
        setupViewModelObserver(view)
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPass = binding.etConfirmPass.text.toString().trim()
            val role = binding.atvRole.text.toString().trim()

            when {
                name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || role.isEmpty() -> {
                    showSnackbar(view, "All fields are required!", isError = true)
                }
                password != confirmPass -> {
                    showSnackbar(view, "Passwords do not match!", isError = true)
                }
                else -> {
                    viewModel.register(name, email, password, confirmPass, role)
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)
        lifecycleScope.launch {
            viewModel.success.collect {
                val action = RegisterFragmentDirections.registerToLogin()
                navController.navigate(action)
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                showSnackbar(view, it, isError = true)
            }
        }
    }
}
