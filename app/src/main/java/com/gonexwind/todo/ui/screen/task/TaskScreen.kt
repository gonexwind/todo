package com.gonexwind.todo.ui.screen.task

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.gonexwind.todo.R
import com.gonexwind.todo.data.model.ToDoTask
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.Action

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask,
                navigateToListScreen = { action ->
                    when {
                        action == Action.NO_ACTION -> navigateToListScreen(action)
                        sharedViewModel.validateFields() -> navigateToListScreen(action)
                        else -> displayToast(context)
                    }
                }
            )
        },
        content = {
            TaskContent(
                title,
                onTitleChange = { sharedViewModel.updateTitle(it) },
                description,
                onDescriptionChange = { sharedViewModel.description.value = it },
                priority,
                onPrioritySelected = { sharedViewModel.priority.value = it }
            )
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(context, context.getString(R.string.fields_empty), Toast.LENGTH_SHORT).show()
}
