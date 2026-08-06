package com.example.taskquotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskquotes.ui.screens.AddTaskScreen
import com.example.taskquotes.ui.screens.LoginScreen
import com.example.taskquotes.ui.screens.TaskListScreen
import com.example.taskquotes.ui.screens.TriviaScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoggedIn = {
                    navController.navigate(Screen.Tasks.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Tasks.route) {
            TaskListScreen(
                onAddTask = { navController.navigate(Screen.AddTask.route) },
                onOpenTrivia = { navController.navigate(Screen.Trivia.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.AddTask.route) {
            AddTaskScreen(onDone = { navController.popBackStack() })
        }

        composable(Screen.Trivia.route) {
            TriviaScreen(onBack = { navController.popBackStack() })
        }
    }
}
