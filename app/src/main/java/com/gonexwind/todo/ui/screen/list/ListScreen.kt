package com.gonexwind.todo.ui.screen.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.gonexwind.todo.R
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.Action
import com.gonexwind.todo.util.SearchAppBarState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) { sharedViewModel.getAllTasks() }

    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val snackBarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        snackBarHostState,
        { sharedViewModel.handleDatabaseAction(action) },
        { sharedViewModel.action.value = it },
        sharedViewModel.title.value,
        action
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { ListAppBar(sharedViewModel, searchAppBarState, searchTextState) },
        content = { innerPadding ->
            ListContent(
                innerPadding,
                allTasks,
                searchedTasks,
                searchAppBarState,
                navigateToTaskScreen
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToTaskScreen(-1) }) {
                Icon(Icons.Filled.Add, stringResource(R.string.add_task))
            }
        }
    )
}

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action,
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackBarHostState.showSnackbar(
                    "${action.name} : $taskTitle",
                    setActionLabel(action),
                )
                undoDeletedTask(action, snackBarResult, onUndoClicked)
            }
        }
    }
}

private fun setActionLabel(action: Action): String = if (action.name == "DELETE") "UNDO" else "OK"

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onClick: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onClick(Action.UNDO)
    }
}