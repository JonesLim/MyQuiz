package com.jones.myquiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jones.myquiz.data.model.Quiz
import com.jones.myquiz.databinding.ItemLayoutQuizBinding

class QuizAdapter(
    private var quizzes :List<Quiz>,
    private val fileName: String
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(child: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLayoutQuizBinding.inflate(
            LayoutInflater.from(child.context),
            child,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun getItemCount() = quizzes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quiz = quizzes[position]
        if (holder is QuizViewHolder) {
            holder.bind(quiz)
        }
    }

    fun setQuiz(quizs: List<Quiz>) {
        this.quizzes = quizs
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: ItemLayoutQuizBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                tvQuizId.text = "Quiz ID: ${quiz.QuizId}"
                tvTitle.text = "Quiz Title: ${quiz.title}"
                tvCreatedBy.text = "Created By: ${quiz.creatorName}"
            }
        }
    }
}