package com.example.quickmath.ui.quiz.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnswerView(
    modifier: Modifier = Modifier,
    answerText: String = "",
    onClick: () -> Unit = {}
) {
    return Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text =answerText, style = MaterialTheme.typography.displaySmall)
        }
    }
}

@Preview(
    showBackground = true,
    name = "AnswerViewLight"
)
@Composable
fun AnswerViewPreview() {
    AnswerView(
        answerText = "12"
    )
}