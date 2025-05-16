# Assignment 2
**Name:** Yamlak Negash  
**ID:** UGR/2910/15  
**Section:** 2

# Todo App

A modern Android application that fetches and displays TODO items from the JSONPlaceholder API. The app showcases the implementation of Jetpack Compose, Room database for local caching, and Retrofit for network operations.

## Libraries Used

- **Jetpack Compose**: Modern UI toolkit
- **Room**: Database for local storage
- **Retrofit**: Type-safe HTTP client
- **Coroutines**: Asynchronous programming
- **ViewModel**: UI state management
- **Flow**: Reactive data streams
- **Material 3**: Design components

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the app

## Features

- Fetch TODO items from [JSONPlaceholder API](https://jsonplaceholder.typicode.com/todos)
- Display a list of todos in a scrollable UI using Jetpack Compose
- Detailed view for each todo item
- Offline support via Room database caching
- Material 3 design
- MVVM architecture
- Dark/Light theme support

## Technical Details

- **Data Flow**:

  1. Data is first loaded from the local database (if available)
  2. The app attempts to refresh data from the network
  3. Updated data is stored in the database
  4. UI observes database changes via Flow

- **Error Handling**:
  - Network errors are caught, and the app continues with cached data
  - Different error states are displayed to the user

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern and utilizes modern Android development practices:

### Data Layer

- **Model**: The `Todo` data class represents the structure of todo items.
- **Room Database**: Local storage for caching the todo items.
  - `TodoDao`: Interface for database operations.
  - `TodoDatabase`: Room database implementation.
- **Retrofit**: Network client for fetching data from the API.
  - `TodoApiService`: Interface defining API endpoints.
  - `NetworkModule`: Provides configured Retrofit instances.
- **Repository**: `TodoRepository` connects the database and network data sources.

### Presentation Layer

- **ViewModels**:
  - `TodoListViewModel`: Manages UI state for the list screen.
  - `TodoDetailViewModel`: Manages UI state for the detail screen.
- **UI State**: Sealed classes represent different UI states:
  - Loading
  - Success
  - Empty
  - Error

### UI Layer

- **Screens**:
  - `TodoListScreen`: Displays a list of all todos.
  - `TodoDetailScreen`: Shows detailed information for a selected todo.
- **Components**:
  - `TodoItem`: Reusable UI component for displaying a todo in the list.
- **Navigation**:
  - `TodoNavigation`: Handles navigation between screens.
  - `NavRoutes`: Defines navigation routes.
