package com.gonexwind.todo.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gonexwind.todo.ui.screen.list.ListScreen
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.Constants.LIST_ARGUMENT_KEY
import com.gonexwind.todo.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        ListScreen(navigateToTaskScreen, sharedViewModel)
    }
}