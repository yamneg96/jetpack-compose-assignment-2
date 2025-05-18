package com.attendance.todoapp.data.repository

import com.attendance.todoapp.data.local.TodoDao
import com.attendance.todoapp.data.model.Todo
import com.attendance.todoapp.data.remote.TodoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TodoRepository(
    private val todoApiService: TodoApiService,
    private val todoDao: TodoDao
) {
    // Get all todos from local database as Flow
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()
    
    // Get a specific todo by ID from local database as Flow
    fun getTodoById(id: Int): Flow<Todo> = todoDao.getTodoById(id)
    
    // Refresh todos from remote API and cache them
    suspend fun refreshTodos() {
        withContext(Dispatchers.IO) {
            try {
                val remoteTodos = todoApiService.getTodos()
                todoDao.insertTodos(remoteTodos)
            } catch (e: Exception) {
                // Handle network errors - the app will continue with cached data
            }
        }
    }
    
    // Fetch a specific todo from API and update local cache
    suspend fun refreshTodoById(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val remoteTodo = todoApiService.getTodoById(id)
                todoDao.insertTodos(listOf(remoteTodo))
            } catch (e: Exception) {
                // Handle network errors - the app will continue with cached data
            }
        }
    }
} 