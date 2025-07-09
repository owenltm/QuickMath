package com.example.quickmath.ui.quiz.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
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
import com.example.quickmath.ui.theme.QuickMathTheme

@Composable
fun TimerView(
    modifier: Modifier = Modifier,
    time: String
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.tertiaryContainer
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    name = "TimerViewLight"
)
@Composable
fun TimerViewPreview() {
    QuickMathTheme {
        TimerView(
            time = "10"
        )
    }
}