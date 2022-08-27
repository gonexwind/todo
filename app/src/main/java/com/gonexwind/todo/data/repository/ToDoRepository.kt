package com.gonexwind.todo.data.repository

import com.gonexwind.todo.data.ToDoDao
import com.gonexwind.todo.data.model.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(
    private val dao: ToDoDao
) {

    val getAllTasks: Flow<List<ToDoTask>> = dao.getAllTasks()
    val sortByLowPriority: Flow<List<ToDoTask>> = dao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = dao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> = dao.getSelectedTask(taskId)

    fun searchTask(query: String): Flow<List<ToDoTask>> = dao.searchTask(query)

    suspend fun addTask(toDoTask: ToDoTask) {
        dao.addTask(toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        dao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        dao.deleteTask(toDoTask)
    }

    suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }
}