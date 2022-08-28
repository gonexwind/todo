package com.gonexwind.todo.ui.screen.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gonexwind.todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Scaffold(
        topBar = { ListAppBar() },
        content = {},
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToTaskScreen(-1) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_task))
            }
        }
    )
}

@Composable
@Preview()
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}