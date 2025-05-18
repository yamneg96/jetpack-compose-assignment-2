package com.attendance.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.attendance.todoapp.data.model.Todo
import com.attendance.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val repository: TodoRepository,
    private val todoId: Int
) : ViewModel() {
    
    // UI states
    private val _uiState = MutableStateFlow<TodoDetailUiState>(TodoDetailUiState.Loading)
    val uiState: StateFlow<TodoDetailUiState> = _uiState.asStateFlow()
    
    init {
        loadTodoDetail()
    }
    
    // Load todo detail from repository
    fun loadTodoDetail() {
        viewModelScope.launch {
            _uiState.value = TodoDetailUiState.Loading
            
            // First try to refresh from network
            try {
                repository.refreshTodoById(todoId)
            } catch (e: Exception) {
                // Network error will be handled, we'll show cached data
            }
            
            // Observe database changes
            repository.getTodoById(todoId)
                .catch { e ->
                    _uiState.value = TodoDetailUiState.Error(e.message ?: "Unknown error")
                }
                .collect { todo ->
                    _uiState.value = TodoDetailUiState.Success(todo)
                }
        }
    }
    
    // Factory for creating the ViewModel with dependencies
    class Factory(
        private val repository: TodoRepository,
        private val todoId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoDetailViewModel::class.java)) {
                return TodoDetailViewModel(repository, todoId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

// Sealed class representing different UI states
sealed class TodoDetailUiState {
    data object Loading : TodoDetailUiState()
    data class Success(val todo: Todo) : TodoDetailUiState()
    data class Error(val message: String) : TodoDetailUiState()
} 