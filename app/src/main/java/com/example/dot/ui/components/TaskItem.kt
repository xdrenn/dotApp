package com.example.dot.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.example.dot.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dot.models.Task
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onCheckedClick: (Boolean) -> Unit
) {
    val delete = SwipeAction(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.delete_item),
                contentDescription = null,
                modifier = Modifier
                    .size(55.dp)
                    .padding(10.dp)
            )
               },
        background =  Color.hsl(206f, 0.55f, 0.47f),
        onSwipe = { onDelete() }
    )
    SwipeableActionsBox(endActions = listOf(delete)) {
        Card(
            modifier = modifier,
            backgroundColor = Color.hsl(215f, 0.29f, 0.77f, 0.3f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart_item),
                        contentDescription = "Tag",
                        modifier = Modifier.size(28.dp),
                        tint = Color(task.color)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.h6,
                        color = Color.hsl(215f, 0.28f, 0.31f ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = task.task,
                        style = MaterialTheme.typography.body1,
                        color =  Color.hsl(215f, 0.28f, 0.31f ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = task.createdDateFormatted,
                        style = MaterialTheme.typography.body1,
                        color =  Color.hsl(215f, 0.28f, 0.31f ),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                }
                Column {
                    Checkbox(
                        checked = task.completed ,
                        onCheckedChange =  onCheckedClick,
                        colors = CheckboxDefaults.colors(checkedColor = Color.hsl(215f, 0.28f, 0.77f )),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}