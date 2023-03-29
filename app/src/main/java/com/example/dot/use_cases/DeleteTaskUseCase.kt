package com.example.dot.use_cases

import com.example.dot.data.TaskRepository
import com.example.dot.models.Task

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {

     suspend operator fun invoke(task: Task){
        taskRepository.deleteTask(task)
    }
}