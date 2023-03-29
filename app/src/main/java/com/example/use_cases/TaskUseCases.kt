package com.example.use_cases

class TaskUseCases (
    val getTasks: GetTasksUseCase,
    val getTask: GetTaskUseCase,
    val deleteTask: DeleteTaskUseCase,
    val addTask: AddTaskUseCase,
)