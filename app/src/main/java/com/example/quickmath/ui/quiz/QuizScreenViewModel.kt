package com.example.quickmath.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quickmath.domain.model.AdditionQuestion
import com.example.quickmath.domain.model.Answer
import com.example.quickmath.domain.model.EmptyQuestion
import com.example.quickmath.domain.model.Question
import com.example.quickmath.domain.model.SubtractionQuestion
import com.example.quickmath.utils.getNumbersAsList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizScreenViewModel: ViewModel() {
    private var _timer = MutableStateFlow<Int>(0);
    val timer = _timer.asStateFlow()

    private var _question = MutableStateFlow<Question>(EmptyQuestion())
    val question = _question.asStateFlow()

    private var _answers = MutableStateFlow(listOf<Answer>())
    val answers = _answers.asStateFlow()

    init {
        generateQuestion()
        startTimer()
    }

    fun startTimer(){
        _timer.value = 10
        viewModelScope.launch {
            tickTimer()
        }
    }

    fun increaseTimer(){
        _timer.value = _timer.value + 3
    }

    fun decreaseTimer(){
        _timer.value = _timer.value - 1
    }

    suspend fun tickTimer(){
        while (_timer.value > 0){
            delay(1000)
            _timer.value = _timer.value - 1
        }
    }

    fun onAnswer(answer: Answer) {
        val isCorrect = _question.value.validate(answer)
        val isHaveTime = _timer.value > 0

        if(!isHaveTime){
            return
        }

        generateQuestion()
        if(isCorrect){
            increaseTimer()
            Log.d("QuizScreenViewModel", "Correct answer")
        } else {
            decreaseTimer()


            
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