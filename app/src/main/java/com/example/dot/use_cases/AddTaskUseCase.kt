package com.example.dot.use_cases

import com.example.dot.data.TaskRepository
import com.example.dot.models.InvalidTaskException
import com.example.dot.models.Task

class AddTaskUseCase(
    private val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task) {
        if(task.title.isBlank()){
            throw InvalidTaskException("The title can't be empty.")
        }
        if(task.task.isBlank()){
            throw InvalidTaskException("The task can't be empty.")
        }
        repository.insertTask(task)

    }
}