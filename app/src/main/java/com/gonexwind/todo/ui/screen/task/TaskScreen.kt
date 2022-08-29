package com.gonexwind.todo.ui.screen.task

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.gonexwind.todo.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(navigateToListScreen: (Action) -> Unit) {
    Scaffold(
        topBar = { TaskAppBar(navigateToListScreen)},
        content = {}
    )
}