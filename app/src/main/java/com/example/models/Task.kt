package com.example.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dot.ui.theme.*
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String,
    val task: String,
    val color: Int,
    val date: Long = System.currentTimeMillis()
) : Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(date)

    companion object {
        val taskColors = listOf(Blue, Coral, Yellow, Green, Pink, DarkPurple, LightGray, LightPink, LightBlue)
    }
}


class InvalidTaskException(message: String): Exception (message)
