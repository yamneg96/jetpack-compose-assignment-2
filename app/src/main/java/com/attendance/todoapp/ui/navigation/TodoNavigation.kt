package com.attendance.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.attendance.todoapp.TodoApplication
import com.attendance.todoapp.ui.screens.TodoDetailScreen
import com.attendance.todoapp.ui.screens.TodoListScreen
import com.attendance.todoapp.ui.viewmodel.TodoDetailViewModel
import com.attendance.todoapp.ui.viewmodel.TodoListViewModel

@Composable
fun TodoNavigation(
    navController: NavHostController = rememberNavController(),
    darkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as TodoApplication
    
    NavHost(
        navController = navController,
        startDestination = NavRoutes.TODO_LIST
    ) {
        // Todo List Screen
        composable(route = NavRoutes.TODO_LIST) {
            // Initialize ViewModel
            val todoListViewModel: TodoListViewModel = viewModel(
                factory = TodoListViewModel.Factory(application.todoRepository)
            )
            
            TodoListScreen(
                onTodoClick = { todo ->
                    // Navigate to detail screen with todo ID
                    navController.navigate(NavRoutes.todoDetailRoute(todo.id))
                },
                todoListViewModel = todoListViewModel
            )
        }
        
        // Todo Detail Screen
        composable(
            route = NavRoutes.TODO_DETAIL,
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            // Get the todoId from the route arguments
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: -1
            
            // Initialize ViewModel with todoId
            val todoDetailViewModel: TodoDetailViewModel = viewModel(
                factory = TodoDetailViewModel.Factory(
                    repository = application.todoRepository,
                    todoId = todoId
                )
            )
            
            TodoDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                todoDetailViewModel = todoDetailViewModel
            )
        }
    }
} 