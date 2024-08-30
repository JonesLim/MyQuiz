package com.jones.myquiz.ui.screens.addquiz

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.jones.myquiz.R
import com.jones.myquiz.databinding.FragmentAddQuizBinding
import com.jones.myquiz.ui.screens.addquiz.viewModel.AddQuizViewModelImpl
import com.jones.myquiz.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class AddQuizFragment : BaseFragment<FragmentAddQuizBinding>() {

    override val viewModel: AddQuizViewModelImpl by viewModels()
    private var shouldNavigateBack = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        val btnBack: LottieAnimationView = binding.btnBack


        btnBack.setOnClickListener {
            navController.popBackStack()
            Log.d("debugging", "setupUiComponents: ")
        }

        binding.run {
            btnCreate.setOnClickListener {
                val quizId = etQuizId.text.toString()
                val title = etTitle.text.toString()

                // Check if quizId, title, and CSV file are not empty before proceeding
                if (quizId.isNotBlank() && title.isNotBlank() && tvSelectedFile.text.isNotBlank()) {
                    viewModel.checkQuizIdExists(quizId) { exists ->
                        lifecycleScope.launch {
                        if (exists) {
                            // Show a Toast message for the error
                            showSnackbar(view, "This Quiz ID has existed! Please try another ID.")
                        } else {
                            val timer = viewModel.timer.value
                            viewModel.addQuiz(quizId, title, timer)
                            val action = AddQuizFragmentDirections.addQuizToTeacherDash()
                            navController.navigate(action)
                        }
                    }
                }
                } else {
                    showSnackbar(view, "Please fill in all fields and select a CSV file!")
                }
            }

            btnCsv.setOnClickListener {
                getContent.launch("text/*")
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val documentFile = DocumentFile.fromSingleUri(requireContext(), it)
                val originalFileName = documentFile?.name
                Log.d("debugging", "Original File Name: $originalFileName")

                binding.tvSelectedFile.text = originalFileName ?: "CSV File Name"

                val csvFile = requireActivity().contentResolver.openInputStream(it)
                val isr = InputStreamReader(csvFile)

                BufferedReader(isr).readLines().let { lines ->
                    Log.d("debugging", lines.toString())
                    viewModel.readCsv(lines)
                }
            }
        }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)
        lifecycleScope.launch {
            viewModel.success.collect {
                if (shouldNavigateBack) {
                    navController.popBackStack()
                    shouldNavigateBack = false // Reset the flag
                }
            }
        }
    }
}
