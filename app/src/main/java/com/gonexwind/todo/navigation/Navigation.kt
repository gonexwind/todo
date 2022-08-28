package com.gonexwind.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gonexwind.todo.navigation.destination.listComposable
import com.gonexwind.todo.navigation.destination.taskComposable
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val screen = remember(navController) {
        Screens(navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}