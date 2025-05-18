package com.attendance.todoapp.data.remote

import com.attendance.todoapp.data.model.Todo
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
    
    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): Todo
    
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
} 