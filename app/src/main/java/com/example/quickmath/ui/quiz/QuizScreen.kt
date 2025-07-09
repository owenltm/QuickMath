package com.example.quickmath.ui.quiz

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickmath.domain.model.Answer
import com.example.quickmath.ui.quiz.composables.AnswerView
import com.example.quickmath.ui.quiz.composables.QuestionView
import com.example.quickmath.ui.quiz.composables.TimerView
import com.example.quickmath.ui.theme.QuickMathTheme

@Composable
fun QuizScreen(
    onNavigateToStartMenu: () -> Unit = {},
    viewModel: QuizScreenViewModel = viewModel(factory = QuizScreenViewModelFactory())
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.gameResult.collect { event ->
            Log.d("QuizScreen", "Game result: ${event}")
        }
    }

    Scaffold() {
        QuizScreenView(
            modifier = Modifier.padding(it),
            time = state.time,
            question = state.question.template,
            answers = state.answers,
            onAnswer = {answer -> viewModel.onAnswer(answer) }
        )
    }
}

@Composable
fun QuizScreenView(
    modifier: Modifier = Modifier,
    time: Int = 0,
    question: String = "",
    answers: List<Answer> = emptyList(),
    onAnswer: (answer: Answer) -> Unit = {}
) {
    Scaffold {
        Column(
            modifier = modifier.padding(it)
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
            ) {
                QuestionView(questionText = question)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(0.2f),
                    ) {
                        TimerView(time = time.toString())
                    }
                }
            }
            Box(modifier = Modifier.fillMaxHeight()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (answers.isNotEmpty()) {
                        for (i in 0..(answers.size - 1) / 2) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                if (answers.size > (i * 2)) {
                                    answers[i * 2].let {
                                        AnswerView(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(0.5f),
                                            onClick = { onAnswer(it) },
                                            answerText = it.value.toString()
                                        )
                                    }
                                }
                                if (answers.size > ((i * 2) + 1)) {
                                    answers[(i * 2) + 1].let {
                                        AnswerView(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(1f),
                                            onClick = { onAnswer(it) },
                                            answerText = it.value.toString()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "LoginScreenLight"
)
@Composable
fun QuizScreenPreview() {
    QuickMathTheme {
        QuizScreenView(
            question = "1 + 2",
            time = 15,
            answers = listOf(
                Answer(3),
                Answer(4),
                Answer(5),
                Answer(6)
            ),
            onAnswer = {}
        )
    }
}
