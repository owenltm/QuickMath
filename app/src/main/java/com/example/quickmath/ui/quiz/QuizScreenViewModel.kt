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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizScreenState(
    val isGameRunning: Boolean = false,
    val time: Int = 0,
    val score: Int = 0,
    val question: Question = EmptyQuestion(),
    val answers: List<Answer> = emptyList<Answer>()
)

sealed class QuizScreenEvent {
    data class GameOverEvent(val message: String) : QuizScreenEvent()
    data class QuestionAnsweredEvent(val isCorrect: Boolean) : QuizScreenEvent()
}

class QuizScreenViewModel: ViewModel() {
    private var _state = MutableStateFlow<QuizScreenState>(QuizScreenState())
    val state = _state.asStateFlow()

    private var _gameResult = MutableSharedFlow<String>()
    val gameResult = _gameResult.asSharedFlow()

    init {
        startGame()
    }

    fun startGame() {
        _state.update {
            it.copy(
                isGameRunning = true
            )
        }
        generateQuestion()
        startTimer()
    }

    fun stopGame() {
        _state.update {
            it.copy(
                isGameRunning = false
            )
        }

        viewModelScope.launch {
            _gameResult.emit("Game Over")
        }
    }

    fun startTimer(){
        _state.update {
            it.copy(
                time = 15
            )
        }
        viewModelScope.launch {
            tickTimer()
        }
    }

    fun increaseTimer(){
        _state.update {
            it.copy(
                time = it.time + 3
            )
        }
    }

    fun decreaseTimer(){
        _state.update {
            it.copy(
                time = it.time - 3
            )
        }
    }

    suspend fun tickTimer(){
        while (state.value.time > 0 && state.value.isGameRunning) {
            _state.update {
                it.copy(
                    time = it.time - 1
                )
            }
            delay(1000)
        }

        if (state.value.time <= 0) {
            stopGame()
        }
    }

    fun onAnswer(answer: Answer) {
        val isCorrect = state.value.question.validate(answer)
        val isHaveTime = state.value.time > 0

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

        when(operator % 2){
            0 -> {
                _state.update { it ->
                    val newQuestion = AdditionQuestion(getNumbersAsList(2))
                    it.copy(
                        question = newQuestion,
                        answers = newQuestion.answers
                    )
                }
            }
            else -> {
                _state.update { it ->
                    val newQuestion = SubtractionQuestion(getNumbersAsList(2))
                    it.copy(
                        question = newQuestion,
                        answers = newQuestion.answers
                    )
                }
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