package com.example.taskquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskquotes.data.local.AppDatabase
import com.example.taskquotes.data.local.TaskEntity
import com.example.taskquotes.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TaskRepository(AppDatabase.getInstance(application).taskDao())

    val tasks: StateFlow<List<TaskEntity>> = repository.getTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(title: String, description: String) {
        if (title.isBlank()) return
        viewModelScope.launch { repository.addTask(title, description) }
    }

    fun toggleDone(task: TaskEntity) {
        viewModelScope.launch { repository.toggleDone(task) }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch { repository.deleteTask(task) }
    }

    fun syncWithCloud(userId: String) {
        viewModelScope.launch { repository.syncWithCloud(userId) }
    }
}
