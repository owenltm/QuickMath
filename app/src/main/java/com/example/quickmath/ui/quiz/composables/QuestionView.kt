package com.example.quickmath.ui.quiz.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickmath.ui.theme.QuickMathTheme

@Composable
fun QuestionView(
    modifier: Modifier = Modifier,
    questionText: String = "",
) {
    return Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(32.dp)
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            questionText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
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
    QuickMathTheme {
        QuestionView(
            questionText = "1 + 1"
        )
    }
}