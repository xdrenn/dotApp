package com.example.dot.use_cases

import com.example.dot.data.TaskRepository
import com.example.dot.models.Task

class CheckedChangeUseCase (
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        return repository.insertTask(task)
    }
}