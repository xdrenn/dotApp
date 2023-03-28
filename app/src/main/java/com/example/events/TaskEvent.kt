package com.example.events

import com.example.models.Task
import com.example.utils.OrderTasks

sealed class TaskEvent{
    data class Order(val orderTasks: OrderTasks): TaskEvent()
    data class DeleteTask(val task: Task): TaskEvent()
    object RestoreTask: TaskEvent()
    object ToggleOrderSection: TaskEvent()
}
