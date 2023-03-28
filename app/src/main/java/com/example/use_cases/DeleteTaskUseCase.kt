package com.example.use_cases

import com.example.data.TaskRepository
import com.example.models.Task

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {

     suspend operator fun invoke(task: Task){
        taskRepository.deleteTask(task)
    }
}