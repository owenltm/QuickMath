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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    val showGameOver = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.gameResult.collect { event ->
            Log.d("QuizScreen", "Game result: ${event}")
            showGameOver.value = true
        }
    }

    when {
        showGameOver.value -> {
            GameOverDialog(
                onConfirm = onNavigateToStartMenu
            )
        }
    }

    Scaffold() {
        QuizScreenView(
            modifier = Modifier.padding(it),
            time = state.time,
            score = state.score,
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
    score: Int = 0,
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
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.2f),
                        ) {
                            TimerView(time = time.toString())
                        }
                        Box(
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                "Score: ${score}",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
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

@Composable
fun GameOverDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.Close, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "GAME OVER")
        },
        text = {
            Text(text = "Total score of 69")
        },
        onDismissRequest = {
            // NOT DISMISSIBLE
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Back to Menu")
            }
        },
    )
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
            score = 10,
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
