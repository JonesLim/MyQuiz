package com.jones.myquiz.ui.screens.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.jones.myquiz.R
import com.jones.myquiz.ui.screens.base.viewModel.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseFragment<T:ViewBinding> : Fragment() {


    abstract val viewModel: BaseViewModel
    lateinit var binding : T
    protected lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         viewModel.onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        setupUiComponents(view)
        setupViewModelObserver(view)
    }

    protected open fun onFragmentResult() {}
    open fun setupUiComponents(view: View) {}

    open fun setupViewModelObserver(view: View) {
        lifecycleScope.launch {
            viewModel.error.collect {
                showSnackbar(view, it)
            }
        }
        lifecycleScope.launch {
            viewModel.success.collect {
                showSnackbar(view, it)
            }
        }
    }

    fun showSnackbar(view: View, msg: String, isError: Boolean = false) {
        if (view.isAttachedToWindow) {
            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)

            if (isError) {
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.colorError)
                )
            } else {
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.mutedPastelYellow)
                ).setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.colorInfo)
                )
            }
            snackbar.show()
        } else {
            Log.e("SnackbarError", "Cannot show Snackbar: View is not attached.")
        }
    }

    fun logMsg(msg: String, tag: String = "debugging") {
        Log.d(tag, msg)
    }

}