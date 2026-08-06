package com.example.taskquotes.data.repository

import com.example.taskquotes.data.local.TaskDao
import com.example.taskquotes.data.local.TaskEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

/**
 * Room es la fuente de verdad local (funciona sin internet). Firestore se usa
 * únicamente como respaldo/sincronización en la nube cuando hay sesión activa
 * y el proyecto de Firebase está configurado.
 */
class TaskRepository(
    private val taskDao: TaskDao,
    private val firestore: FirebaseFirestore? = runCatching { FirebaseFirestore.getInstance() }.getOrNull()
) {

    fun getTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun addTask(title: String, description: String) {
        taskDao.insertTask(TaskEntity(title = title, description = description))
    }

    suspend fun toggleDone(task: TaskEntity) {
        taskDao.updateTask(
            task.copy(isDone = !task.isDone, synced = false, updatedAt = System.currentTimeMillis())
        )
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
        firestore?.let { db ->
            runCatching {
                db.collection("tasks").document(task.id.toString()).delete().await()
            }
        }
    }

    /** Sube a Firestore las tareas locales pendientes de sincronizar. */
    suspend fun syncWithCloud(userId: String) {
        val db = firestore ?: return
        val pending = taskDao.getUnsyncedTasks()
        pending.forEach { task ->
            runCatching {
                db.collection("users").document(userId)
                    .collection("tasks").document(task.id.toString())
                    .set(task)
                    .await()
                taskDao.markSynced(task.id)
            }
        }
    }
}
