package com.example.taskquotes.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Tasks : Screen("tasks")
    object AddTask : Screen("add_task")
    object Quotes : Screen("quotes")
}
