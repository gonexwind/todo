package com.gonexwind.todo.ui.screen.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gonexwind.todo.R
import com.gonexwind.todo.data.model.Priority
import com.gonexwind.todo.data.model.ToDoTask
import com.gonexwind.todo.util.Action

@Composable
fun TaskAppBar(navigateToListScreen: (Action) -> Unit) {
    NewTaskAppBar(navigateToListScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(navigateToListScreen: (Action) -> Unit) {
    SmallTopAppBar(
        navigationIcon = { BackAction(navigateToListScreen) },
        title = { Text(stringResource(R.string.add_task)) },
        actions = { AddAction(navigateToListScreen) }
    )
}

@Composable
fun BackAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.NO_ACTION) }) {
        Icon(Icons.Filled.ArrowBack, stringResource(R.string.back_arrow))
    }
}

@Composable
fun AddAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.ADD) }) {
        Icon(Icons.Filled.Check, stringResource(R.string.add_task))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    SmallTopAppBar(
        navigationIcon = { CloseAction(navigateToListScreen) },
        title = { Text(selectedTask.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        actions = {
            DeleteAction(navigateToListScreen)
            UpdateAction(navigateToListScreen)
        }
    )
}


@Composable
fun CloseAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.NO_ACTION) }) {
        Icon(Icons.Filled.Close, stringResource(R.string.close))
    }
}

@Composable
fun DeleteAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.DELETE) }) {
        Icon(Icons.Filled.Delete, stringResource(R.string.delete))
    }
}


@Composable
fun UpdateAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.UPDATE) }) {
        Icon(Icons.Filled.Check, stringResource(R.string.delete))
    }
}

@Preview
@Composable
fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScreen = {})
}
@Preview
@Composable
fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(
        ToDoTask(0, "Fikky", "Developer", Priority.LOW),
        navigateToListScreen = {}
    )
}