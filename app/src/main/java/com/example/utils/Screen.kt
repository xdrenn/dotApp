package com.example.utils

sealed class Screen(
    val route: String
){
    object TasksScreen: Screen("task_screen")
    object AddEditScreen: Screen("add_edit_screen")
}
