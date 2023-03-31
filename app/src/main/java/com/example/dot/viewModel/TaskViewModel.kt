package com.example.dot.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dot.ui.presentation.TasksState
import com.example.dot.events.TaskEvent
import com.example.dot.models.Task
import com.example.dot.use_cases.TaskUseCases
import com.example.dot.utils.OrderTasks
import com.example.dot.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TasksState())
    val state: State<TasksState> = _state

    private var recentlyDeletedTask: Task? = null
    private var getTasksJob : Job? = null

    init{
        getTasks(OrderTasks.Date(OrderType.Descending))
    }

    fun onEvent(event: TaskEvent) {
        when (event){
            is TaskEvent.Order -> {
                if(state.value.taskOrder == event.orderTasks &&
                        state.value.taskOrder.orderType == event.orderTasks.orderType
                ){
                    return
                }
                getTasks(event.orderTasks)

            }
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeletedTask = event.task
                }

            }
            is TaskEvent.RestoreTask -> {
                viewModelScope.launch {
                    taskUseCases.addTask(recentlyDeletedTask ?: return@launch)
                    recentlyDeletedTask = null
                }

            }
            is TaskEvent.ToggleOrderSection -> {
              _state.value = state.value.copy(
                  isOrderSectionVisible = !state.value.isOrderSectionVisible
              )
            }
            is TaskEvent.CheckedChange -> {
               viewModelScope.launch {
                 taskUseCases.checkedChange(event.task.copy(
                     completed = event.isCompleted
                    )
                 )
               }

            }
        }
    }
    private fun getTasks(orderTasks: OrderTasks){
        getTasksJob?.cancel()
        taskUseCases.getTasks(orderTasks)
            .onEach { tasks ->
                _state.value = state.value.copy(
                    tasks = tasks,
                    taskOrder = orderTasks
                )
            }
            .launchIn(viewModelScope)
    }
}