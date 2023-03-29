package com.example.dot.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dot.R
import com.example.dot.ui.components.TransparentTextField
import com.example.events.AddEditEvent
import com.example.models.Task
import com.example.viewModel.AddEditViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val titleState = viewModel.taskTitle.value
    val descriptionState = viewModel.taskDescription.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
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
                backgroundColor = Color.LightGray
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.save_item),
                    tint = Color.Black,
                    contentDescription = "Save task",
                    modifier = Modifier.size(22.dp)
                )
            }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(id = R.drawable.background2), contentScale = ContentScale.FillBounds)
        ) {

                Spacer(modifier = Modifier.height(20.dp))
            TransparentTextField(
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

            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Choose tag:",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis)

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
                                .size(35.dp)
                                .border(
                                    width = 3.dp,
                                    shape = heart(),
                                    color = if (viewModel.tagColor.value == colorInt) {
                                        Color.Black
                                    } else Color.Transparent
                                ),

                            painter = painterResource(id = R.drawable.heart_item),
                            contentDescription = "Tag",
                            tint = color
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            TransparentTextField(
                text = descriptionState.text,
                hint = descriptionState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEvent.EnterTask(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.ChangeTaskFocus(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                textStyle = MaterialTheme.typography.h6
            )
        }
    }
}
@Composable
fun heart(): GenericShape {
    return GenericShape { size, _ ->
        heartPath(size = size)
    }
}

fun Path.heartPath(size: Size): Path {

    val width: Float = size.width
    val height: Float = size.height

    // Starting point
    moveTo(width / 2, height / 5)

    // Upper left path
    cubicTo(
        5 * width / 14, 0f,
        0f, height / 15,
        width / 28, 2 * height / 5
    )

    // Lower left path
    cubicTo(
        width / 14, 2 * height / 3,
        3 * width / 7, 5 * height / 6,
        width / 2, height
    )

    // Lower right path
    cubicTo(
        4 * width / 7, 5 * height / 6,
        13 * width / 14, 2 * height / 3,
        27 * width / 28, 2 * height / 5
    )

    // Upper right path
    cubicTo(
        width, height / 15,
        9 * width / 14, 0f,
        width / 2, height / 5
    )
    return this
}