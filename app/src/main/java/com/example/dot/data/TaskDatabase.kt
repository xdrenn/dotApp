package com.example.dot.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dot.models.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "task_db"
    }
}