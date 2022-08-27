package com.gonexwind.todo.navigation

import androidx.navigation.NavHostController
import com.gonexwind.todo.util.Action
import com.gonexwind.todo.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {

    val list: (Action) -> Unit = {
        navController.navigate("list/${it.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = {
        navController.navigate("task/$it")
    }
}