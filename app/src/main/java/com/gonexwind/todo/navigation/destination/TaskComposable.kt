package com.gonexwind.todo.navigation.destination

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gonexwind.todo.ui.screen.task.TaskScreen
import com.gonexwind.todo.util.Action
import com.gonexwind.todo.util.Constants.TASK_ARGUMENT_KEY
import com.gonexwind.todo.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(navigateToListScreen: (Action) -> Unit) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {
        val taskId = it.arguments!!.getInt(TASK_ARGUMENT_KEY)
        Log.d("TaskComposable", taskId.toString())

        TaskScreen(navigateToListScreen)
    }
}