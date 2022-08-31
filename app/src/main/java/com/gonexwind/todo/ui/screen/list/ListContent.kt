package com.gonexwind.todo.ui.screen.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.gonexwind.todo.R
import com.gonexwind.todo.data.model.Priority
import com.gonexwind.todo.data.model.ToDoTask
import com.gonexwind.todo.ui.theme.HighPriorityColor
import com.gonexwind.todo.ui.theme.LARGEST_PADDING
import com.gonexwind.todo.ui.theme.LARGE_PADDING
import com.gonexwind.todo.ui.theme.PRIORITY_INDICATOR_SIZE
import com.gonexwind.todo.util.Action
import com.gonexwind.todo.util.RequestState
import com.gonexwind.todo.util.SearchAppBarState

@Composable
fun ListContent(
    innerPadding: PaddingValues,
    allTasks: RequestState<List<ToDoTask>>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> if (searchedTasks is RequestState.Success) {
                HandleListContent(
                    innerPadding,
                    searchedTasks.data,
                    onSwipeToDelete,
                    navigateToTaskScreen
                )
            }
            sortState.data == Priority.NONE -> if (allTasks is RequestState.Success) {
                HandleListContent(
                    innerPadding,
                    allTasks.data,
                    onSwipeToDelete,
                    navigateToTaskScreen
                )
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    innerPadding,
                    lowPriorityTasks,
                    onSwipeToDelete,
                    navigateToTaskScreen
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    innerPadding,
                    highPriorityTasks,
                    onSwipeToDelete,
                    navigateToTaskScreen
                )
            }
        }
    }
}

@Composable
fun HandleListContent(
    innerPadding: PaddingValues,
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) EmptyContent()
    else DisplayTasks(innerPadding, tasks, onSwipeToDelete, navigateToTaskScreen)
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun DisplayTasks(
    innerPadding: PaddingValues,
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.consumedWindowInsets(innerPadding),
        contentPadding = innerPadding
    ) {
        items(items = tasks, key = { it.id }) { task ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                onSwipeToDelete(Action.DELETE, task)
            }
            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0f else -45f
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(0.2f) },
                background = { RedBackground(degrees) },
                dismissContent = {
                    TaskItem(task, navigateToTaskScreen)
                }
            )
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            Icons.Filled.Delete,
            stringResource(R.string.delete),
            tint = Color.White,
            modifier = Modifier.rotate(degrees)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        onClick = { navigateToTaskScreen(toDoTask.id) },
    ) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "",
            description = "",
            priority = Priority.MEDIUM
        ),
        navigateToTaskScreen = {}
    )
}