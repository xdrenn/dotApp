package com.example.dot.use_cases

import com.example.dot.data.TaskRepository
import com.example.dot.models.Task
import com.example.dot.utils.OrderTasks
import com.example.dot.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasksUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(
        orderTasks: OrderTasks = OrderTasks.Date(OrderType.Ascending)
    ): Flow<List<Task>>{
        return taskRepository.getTasks().map { tasks ->
            when(orderTasks.orderType){
                is OrderType.Ascending -> {
                    when(orderTasks){
                        is OrderTasks.Title -> tasks.sortedBy { it.title.lowercase() }
                            is OrderTasks.Date -> tasks.sortedBy { it.date }
                    }
                }
                is OrderType.Descending -> {
                    when(orderTasks){
                        is OrderTasks.Title -> tasks.sortedByDescending { it.title.lowercase() }
                        is OrderTasks.Date -> tasks.sortedByDescending { it.date }
                    }
                }
            }
        }
    }
}