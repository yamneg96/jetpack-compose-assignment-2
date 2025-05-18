package com.attendance.todoapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Black and white dark theme
private val BlackWhiteDarkColorScheme = darkColorScheme(
    primary = White,
    onPrimary = Black,
    primaryContainer = DarkGray,
    onPrimaryContainer = White,
    secondary = LightGray,
    onSecondary = Black,
    background = Black,
    onBackground = White,
    surface = DarkGray,
    onSurface = White,
    error = White,
    onError = DarkGray
)

// Black and white light theme
private val BlackWhiteLightColorScheme = lightColorScheme(
    primary = Black,
    onPrimary = White,
    primaryContainer = LightGray,
    onPrimaryContainer = Black,
    secondary = DarkGray,
    onSecondary = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    error = Black,
    onError = White
)

@Composable
fun TodoappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is now disabled to use our black and white theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        BlackWhiteDarkColorScheme
    } else {
        BlackWhiteLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            // Set the status bar icons color based on the background color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}