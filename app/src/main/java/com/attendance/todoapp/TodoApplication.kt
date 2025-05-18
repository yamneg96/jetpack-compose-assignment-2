package com.attendance.todoapp

import android.app.Application
import com.attendance.todoapp.data.local.TodoDatabase
import com.attendance.todoapp.data.remote.NetworkModule
import com.attendance.todoapp.data.repository.TodoRepository

class TodoApplication : Application() {
    // Repositories and services that will be used throughout the app
    val database by lazy { TodoDatabase.getDatabase(this) }
    val todoApiService by lazy { NetworkModule.provideTodoApiService() }
    val todoRepository by lazy { TodoRepository(todoApiService, database.todoDao()) }
} 