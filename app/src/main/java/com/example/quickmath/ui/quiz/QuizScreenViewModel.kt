package com.example.quickmath.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizScreenViewModel: ViewModel() {
    private var _question = MutableStateFlow("")
    val question = _question.asStateFlow()

    private var _answers = MutableStateFlow(listOf<Int>())
    val answers = _answers.asStateFlow()

    init {
        generateQuestion()
    }

    fun onAnswer() {
        generateQuestion()
    }

    private fun generateQuestion() {
        val firstNumber = (Math.random() * 10).toInt()
        val secondNumber = (Math.random() * 10).toInt()
        val operator = listOf<String>("-", "+")[Math.random().toInt() * 2]

        _question.value = "$firstNumber $operator $secondNumber"

        var realAnswer = Integer.MIN_VALUE
        if(operator == "-"){
            realAnswer = firstNumber - secondNumber
        } else {
            realAnswer = firstNumber + secondNumber
        }

        val answerChoices = mutableListOf<Int>(realAnswer)
        repeat(3){
            val random = (Math.random() * realAnswer * 2).toInt()
            answerChoices.add(random)
        }
        _answers.value = answerChoices
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