package com.example.dot.ui.presentation

import com.example.models.Task
import com.example.utils.OrderTasks
import com.example.utils.OrderType

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: OrderTasks = OrderTasks.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
