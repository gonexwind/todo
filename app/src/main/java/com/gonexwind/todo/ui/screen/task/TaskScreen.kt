package com.gonexwind.todo.ui.screen.task

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.gonexwind.todo.data.model.ToDoTask
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    Scaffold(
        topBar = { TaskAppBar(selectedTask, navigateToListScreen) },
        content = {
            TaskContent(
                title,
                onTitleChange = { sharedViewModel.title.value = it },
                description,
                onDescriptionChange = { sharedViewModel.description.value = it },
                priority,
                onPrioritySelected = { sharedViewModel.priority.value = it }
            )
        }
    )
}