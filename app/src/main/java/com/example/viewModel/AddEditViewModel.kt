package com.example.viewModel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dot.ui.presentation.TaskTextState
import com.example.events.AddEditEvent
import com.example.models.InvalidTaskException
import com.example.models.Task
import com.example.use_cases.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskTitle  = mutableStateOf(TaskTextState(
        hint = "Title"
    ))
    val taskTitle: State<TaskTextState> = _taskTitle

    private val _taskDescription  = mutableStateOf(TaskTextState(
        hint = "Task"
    ))
    val taskDescription: State<TaskTextState> = _taskDescription

    private val _tagColor  = mutableStateOf<Int>(Task.taskColors.random().toArgb())
    val tagColor: State<Int> = _tagColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    init{
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if(taskId != -1){
                viewModelScope.launch {
                    taskUseCases.getTask(taskId)?.also{ task ->
                        currentTaskId = task.id
                        _taskTitle.value = taskTitle.value.copy(
                            text = task.title,
                            isHintVisible = false
                        )
                        _taskDescription.value = taskDescription.value.copy(
                            text = task.task,
                            isHintVisible = false
                        )
                        _tagColor.value = task.color
                    }
                }
            }
        }

    }

    fun onEvent(event: AddEditEvent){
        when(event){
           is  AddEditEvent.EnterTitle -> {
               _taskTitle.value = taskTitle.value.copy(
                   text = event.value
               )
           }
            is AddEditEvent.ChangeTitleFocus -> {
                _taskTitle.value = taskTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskTitle.value.text.isBlank()
                )

            }
            is  AddEditEvent.EnterTask -> {
                _taskDescription.value = taskDescription.value.copy(
                    text = event.value
                )
            }
            is AddEditEvent.ChangeTaskFocus -> {
                _taskDescription.value = taskDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskDescription.value.text.isBlank()
                )
        }
            is AddEditEvent.ChangeColor -> {
                _tagColor.value = event.color
            }
            is AddEditEvent.SaveTask -> {
                viewModelScope.launch {
             try{
                 taskUseCases.addTask(
                     Task(
                         id = currentTaskId,
                         title = taskTitle.value.text,
                         task = taskDescription.value.text,
                         color = tagColor.value,
                         date = System.currentTimeMillis()
                     )
                 )
                 _eventFlow.emit(UIEvent.SaveTask)
                 } catch (e: InvalidTaskException){
                     _eventFlow.emit(
                         UIEvent.ShowSnackBar(
                             message = e.message ?: "Unknown error"
                         )
                     )
             }
                }
            }
        }
    }

    sealed class UIEvent{
        data class ShowSnackBar(val message: String): UIEvent()
        object SaveTask: UIEvent()
    }
}