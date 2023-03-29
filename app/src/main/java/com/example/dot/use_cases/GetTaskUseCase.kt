package com.example.dot.use_cases

import com.example.dot.data.TaskRepository
import com.example.dot.models.Task

class GetTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: Int): Task? {
        return repository.getTaskById(id)
    }
}