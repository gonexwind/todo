package com.gonexwind.todo.ui.screen.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.gonexwind.todo.R
import com.gonexwind.todo.ui.viewmodel.SharedViewModel
import com.gonexwind.todo.util.SearchAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    Scaffold(
        topBar = { ListAppBar(sharedViewModel, searchAppBarState, searchTextState) },
        content = { ListContent() },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToTaskScreen(-1) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_task)
                )
            }
        }
    )
}