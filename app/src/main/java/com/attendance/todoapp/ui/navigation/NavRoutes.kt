package com.attendance.todoapp.ui.navigation

/**
 * Navigation routes for the app
 */
object NavRoutes {
    const val TODO_LIST = "todo_list"
    const val TODO_DETAIL = "todo_detail/{todoId}"
    
    // Helper function to create the todo detail route with a specific ID
    fun todoDetailRoute(todoId: Int) = "todo_detail/$todoId"
} 