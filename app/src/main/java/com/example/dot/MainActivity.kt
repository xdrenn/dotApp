package com.example.dot


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dot.ui.screens.TasksScreen
import com.example.dot.ui.screens.AddEditScreen
import com.example.dot.ui.theme.DotTheme
import com.example.dot.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DotTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.TasksScreen.route
                    ) {
                        composable(route = Screen.TasksScreen.route) {
                            TasksScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditScreen.route +
                                    "?taskId={taskId}",
                            arguments = listOf(
                                navArgument(
                                    name = "taskId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1

                                },
                                navArgument(
                                    name = "taskColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1

                                }
                            )
                        ) {
                            AddEditScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

