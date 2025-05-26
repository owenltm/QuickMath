package com.example.quickmath.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quickmath.domain.model.AdditionQuestion
import com.example.quickmath.domain.model.Answer
import com.example.quickmath.domain.model.EmptyQuestion
import com.example.quickmath.domain.model.Question
import com.example.quickmath.domain.model.SubtractionQuestion
import com.example.quickmath.utils.getNumbersAsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizScreenViewModel: ViewModel() {
    private var _question = MutableStateFlow<Question>(EmptyQuestion())
    val question = _question.asStateFlow()

    private var _answers = MutableStateFlow(listOf<Answer>())
    val answers = _answers.asStateFlow()

    init {
        generateQuestion()
    }

    fun onAnswer(answer: Answer) {
        val isCorrect = _question.value.validate(answer)

        if(isCorrect){
            generateQuestion()
            Log.d("QuizScreenViewModel", "Correct answer")
        } else {
            Log.d("QuizScreenViewModel", "Incorrect answer")
        }
    }

    private fun generateQuestion() {
        val operator = (Math.random() * 10).toInt()

        // TODO: UPDATE ANSWER VALUE AS 1 STATE UPDATE
        when(operator % 2){
            0 -> {
                _question.value = AdditionQuestion(getNumbersAsList(2))
                _answers.value = question.value.answers
            }
            else -> {
                _question.value = SubtractionQuestion(getNumbersAsList(2))
                _answers.value = question.value.answers
            }
        }

    }
}

class QuizScreenViewModelFactory(): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuizScreenViewModel::class.java)){
            return QuizScreenViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}