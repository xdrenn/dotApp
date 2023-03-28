package com.example.dot.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dot.R
import com.example.dot.ui.components.TransperentTextField
import com.example.events.AddEditEvent
import com.example.models.Task
import com.example.viewModel.AddEditViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    navController: NavController,
    tagColor: Int,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val titleState = viewModel.taskTitle.value
    val descriptionState = viewModel.taskDescription.value

    val scaffoldState = rememberScaffoldState()

    val tagColorAnimation = remember {
        Animatable(
            Color(if (tagColor != -1) tagColor else viewModel.tagColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditViewModel.UIEvent.ShowSnackBar -> {
                   scaffoldState.snackbarHostState.showSnackbar(
                       message = event.message
                   )
                }
                is AddEditViewModel.UIEvent.SaveTask -> {
                    navController.navigateUp()
                }
            }
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditEvent.SaveTask)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save task"
                )
            }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Task.taskColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    scope.launch {
                                        viewModel.onEvent(AddEditEvent.ChangeColor(colorInt))
                                    }
                                }
                                .size(35.dp),

                            painter = painterResource(id = R.drawable.heart_item),
                            contentDescription = "Tag",
                            tint = color
                        )

                    }
                }
                }

            Spacer(modifier = Modifier.height(16.dp))
            TransperentTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                                viewModel.onEvent(AddEditEvent.EnterTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
            )

            Spacer(modifier = Modifier.height(16.dp))
            TransperentTextField(
                text = descriptionState.text,
                hint = descriptionState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEvent.EnterTask(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.ChangeTaskFocus(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxSize()
            )

        }

    }

}