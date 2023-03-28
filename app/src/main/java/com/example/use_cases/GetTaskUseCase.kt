package com.example.use_cases

import com.example.data.TaskRepository
import com.example.models.Task

class GetTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: Int): Task? {
        return repository.getTaskById(id)
    }
}