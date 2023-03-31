package com.example.dot.events

import com.example.dot.models.Task
import com.example.dot.utils.OrderTasks

sealed class TaskEvent{
    data class Order(val orderTasks: OrderTasks): TaskEvent()
    data class DeleteTask(val task: Task): TaskEvent()
    data class CheckedChange(val task: Task, val isCompleted: Boolean): TaskEvent()
    object RestoreTask: TaskEvent()
    object ToggleOrderSection: TaskEvent()
}
