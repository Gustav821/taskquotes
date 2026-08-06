package com.example.taskquotes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskquotes.viewmodel.AuthViewModel
import com.example.taskquotes.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onAddTask: () -> Unit,
    onOpenTrivia: () -> Unit,
    onLogout: () -> Unit,
    taskViewModel: TaskViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val tasks by taskViewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Tareas") },
                actions = {
                    IconButton(onClick = onOpenTrivia) {
                        Icon(Icons.Default.Quiz, contentDescription = "Trivia")
                    }
                    IconButton(onClick = {
                        authViewModel.currentUserId?.let { taskViewModel.syncWithCloud(it) }
                    }) {
                        Icon(Icons.Default.Sync, contentDescription = "Sincronizar")
                    }
                    IconButton(onClick = {
                        authViewModel.signOut()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Salir")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes tareas todavía. Agrega una con el botón +")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(tasks, key = { it.id }) { task ->
                    ListItem(
                        headlineContent = {
                            Text(
                                task.title,
                                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                            )
                        },
                        supportingContent = { Text(task.description) },
                        leadingContent = {
                            Checkbox(
                                checked = task.isDone,
                                onCheckedChange = { taskViewModel.toggleDone(task) }
                            )
                        },
                        trailingContent = {
                            IconButton(onClick = { taskViewModel.deleteTask(task) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
