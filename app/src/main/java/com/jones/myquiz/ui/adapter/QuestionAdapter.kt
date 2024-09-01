package com.jones.myquiz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jones.myquiz.data.model.Quiz
import com.jones.myquiz.databinding.FragmentQuizBinding

class QuestionAdapter(
    private var quizs: List<Quiz>,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(child: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentQuizBinding.inflate(
            LayoutInflater.from(child.context),
            child,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun getItemCount() = quizs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quiz = quizs[position]
        if (holder is QuizAdapter.QuizViewHolder) {
            holder.bind(quiz)
        }
    }

    fun setQuiz(quizs: List<Quiz>) {
        this.quizs = quizs
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: FragmentQuizBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                // Assuming that titles and options lists are non-empty
                val questionTitle = quiz.questionTitles.firstOrNull() ?: ""
                tvQuestion.text = questionTitle

                // Assuming that options list has at least 4 items
                option1.text = quiz.options.getOrNull(0) ?: ""
                option2.text = quiz.options.getOrNull(1) ?: ""
                option3.text = quiz.options.getOrNull(2) ?: ""
                option4.text = quiz.options.getOrNull(3) ?: ""
            }
        }
    }
}