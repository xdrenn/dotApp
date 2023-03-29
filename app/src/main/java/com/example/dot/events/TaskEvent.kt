package com.example.dot.events

import com.example.dot.models.Task
import com.example.dot.utils.OrderTasks

sealed class TaskEvent{
    data class Order(val orderTasks: OrderTasks): TaskEvent()
    data class DeleteTask(val task: Task): TaskEvent()
    object RestoreTask: TaskEvent()
    object ToggleOrderSection: TaskEvent()
}
