package com.jones.myquiz.ui.screens.leaderboard

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jones.myquiz.databinding.FragmentLeaderboardBinding
import com.jones.myquiz.ui.adapter.ScoreAdapter
import com.jones.myquiz.ui.screens.base.BaseFragment
import com.jones.myquiz.ui.screens.leaderboard.viewModel.LeaderBoardViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeaderBoardFragment : BaseFragment<FragmentLeaderboardBinding>() {

    override val viewModel: LeaderBoardViewModelImpl by viewModels()
    private lateinit var adapter: ScoreAdapter
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private val mutableCategoryList = mutableListOf<String>() // Mutable list for adapter
    protected var categorySelect = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        setupAdapter()

        categoryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_dropdown_item,
            mutableCategoryList // Use mutable list here
        )

        binding.run {
            autoCompleteCategory.setAdapter(categoryAdapter)
            autoCompleteCategory.setOnClickListener {
                if (mutableCategoryList.isNotEmpty()) {
                    autoCompleteCategory.showDropDown()
                }
            }
            autoCompleteCategory.addTextChangedListener {
                categorySelect = it.toString()
                Log.d("debugging", categorySelect)
                filterResultsByCategory(categorySelect)
            }
        }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.score.collect { results ->
                val uniqueQuizIds = results.map { it.quizId }.toSet().toList()
                Log.d("debugging", uniqueQuizIds.toString())

                mutableCategoryList.clear()
                mutableCategoryList.addAll(uniqueQuizIds)
                categoryAdapter.notifyDataSetChanged()

                // Initially display all results
                adapter.setScore(results)
            }
        }
    }

    private fun setupAdapter() {
        adapter = ScoreAdapter(emptyList())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLeaderBoard.adapter = adapter
        binding.rvLeaderBoard.layoutManager = layoutManager
    }

    private fun filterResultsByCategory(category: String) {
        lifecycleScope.launch {
            viewModel.score.collect { results ->
                val filteredResults = results.filter { it.quizId == category }
                adapter.setScore(filteredResults)
            }
        }
    }
}
