package com.example.dot.models

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dot.ui.theme.*
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String,
    val task: String,
    val color: Int,
    val completed: Boolean = false,
    val date: Long = System.currentTimeMillis()
) : Parcelable {
    val createdDateFormatted: String
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("HH:mm  MMM dd").format(date)

    companion object {
        val taskColors = listOf(Blue, ShadowBlue, Purple, Green, DarkPurple, LightGray, LightBlue, LightPink)
    }
}


class InvalidTaskException(message: String): Exception (message)
