package com.example.data

import com.example.models.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
    ): TaskRepository {

    override fun getTasks(): Flow<List<Task>> {
       return taskDao.getAllTasks()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        return taskDao.insert(task)
    }

    override suspend fun deleteTask(task: Task) {
        return taskDao.delete(task)
    }
}