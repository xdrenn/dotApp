package com.example.dot.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import com.example.dot.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dot.models.Task

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = Color.hsl(0.0f, 0.32f, 0.32f, 0.3f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                painter = painterResource(id = R.drawable.heart_item),
                contentDescription = "Tag",
                modifier = Modifier.size(30.dp),
                tint = Color(task.color)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = task.task,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_item),
                        contentDescription = "Delete",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}


