package com.example.quickmath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quickmath.ui.quiz.QuizScreen
import com.example.quickmath.ui.theme.QuickMathTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMathTheme(
                dynamicColor = false
            ) {
                QuizScreen()
            }
        }
    }
}