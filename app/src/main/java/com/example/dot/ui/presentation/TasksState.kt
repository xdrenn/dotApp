package com.example.dot.ui.presentation

import com.example.dot.models.Task
import com.example.dot.utils.OrderTasks
import com.example.dot.utils.OrderType

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: OrderTasks = OrderTasks.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
