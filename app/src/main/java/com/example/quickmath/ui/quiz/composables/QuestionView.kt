package com.example.quickmath.ui.quiz.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun QuestionView(
    modifier: Modifier = Modifier,
    questionText: String = "",
) {
    return Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            questionText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge.copy(fontSize = 100.sp)
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 500,
    name = "QuestionViewLight"
)
@Composable
fun QuestionViewPreview() {
    QuestionView(
        questionText = "1 + 1"
    )
}