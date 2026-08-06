package com.example.taskquotes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Entidad de Room que representa una tarea guardada localmente. */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val isDone: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)
