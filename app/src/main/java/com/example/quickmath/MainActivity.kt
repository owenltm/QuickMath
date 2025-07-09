package com.example.quickmath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickmath.ui.quiz.QuizScreen
import com.example.quickmath.ui.startmenu.StartMenuScreen
import com.example.quickmath.ui.theme.QuickMathTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickMathTheme(
                dynamicColor = false
            ) {
                val navController = rememberNavController()

                QuickMathNavHost(
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun QuickMathNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "startMenu"
    ) {
        composable("startMenu") {
            StartMenuScreen(
                onStartGame = { navController.navigate("quiz") }
            )
        }
        composable("quiz") {
            QuizScreen(
                onNavigateToStartMenu = { navController.popBackStack() }
            )
        }
    }
}