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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuizScreen(
    viewModel: QuizScreenViewModel = viewModel(factory = QuizScreenViewModelFactory())
) {
    val question by viewModel.question.collectAsState()
    val answers by viewModel.answers.collectAsState()

    Scaffold() {
        QuizScreenView(
            modifier = Modifier.padding(it),
            question = question,
            answers = answers.map {answer -> "$answer" },
            onAnswer = { viewModel.onAnswer() }
        )
    }
}

@Composable
fun QuizScreenView(
    modifier: Modifier = Modifier,
    question: String = "",
    answers: List<String> = emptyList(),
    onAnswer: () -> Unit = {}
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = question, textAlign = TextAlign.Center)
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
                            if(answers.size > (i * 2)){
                                answers[i * 2].let {
                                    Button(
                                        onClick = { onAnswer() },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.5f)
                                    ) {
                                        Text(text = it)
                                    }
                                }
                            }
                            if(answers.size > ((i * 2) + 1)){
                                answers[(i * 2) + 1].let {
                                    Button(
                                        onClick = { onAnswer() },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                    ) {
                                        Text(text = it)
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
    QuizScreenView(
        question = "1 + 2",
        answers = listOf("1", "2", "3", "4"),
        onAnswer = {}
    )
}
