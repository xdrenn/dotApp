@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.dot.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dot.R
import com.example.dot.ui.components.OrderSection
import com.example.dot.ui.components.TaskItem
import com.example.dot.events.TaskEvent
import com.example.dot.utils.Screen
import com.example.dot.viewModel.TaskViewModel
import kotlinx.coroutines.launch

@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditScreen.route)
                },
                backgroundColor = Color.hsl(206f, 0.29f, 0.80f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_item),
                    contentDescription = "Add task",
                    tint = Color.hsl(206f, 0.29f, 0.29f),
                    modifier = Modifier.size(22.dp)
                )
            }

        }, scaffoldState = scaffoldState,
           snackbarHost = {
               SnackbarHost(it) { data ->
                   Snackbar(contentColor = Color.White, backgroundColor = Color.Gray, actionColor = Color.White, snackbarData = data)
               }
           }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(TaskEvent.ToggleOrderSection)
                    })
                {
                    Icon(
                        painter = painterResource(id = R.drawable.sort),
                        contentDescription = "Sort",
                        tint = Color.hsl(206f, 0.29f, 0.29f),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    orderTasks = state.taskOrder,
                    onOrderChange = {
                        viewModel.onEvent(TaskEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.tasks) { task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditScreen.route +
                                            "?taskId=${task.id}"
                                )
                            },

                        onDelete = {
                            viewModel.onEvent(TaskEvent.DeleteTask(task))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Task deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(TaskEvent.RestoreTask)
                                }
                            }
                        },
                        onCheckedClick = { isChecked ->
                           viewModel.onEvent(TaskEvent.CheckedChange(task, isChecked))
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}