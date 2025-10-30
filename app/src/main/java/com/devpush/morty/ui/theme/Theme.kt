package com.devpush.morty.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Rick & Morty Dark Theme - Primary theme for the sci-fi aesthetic
private val RickMortyDarkColorScheme = darkColorScheme(
    primary = RickAction,                    // Bright cyan-green for primary actions
    onPrimary = RickPrimary,                // Dark text on primary
    primaryContainer = RickActionSecondary,  // Purple container
    onPrimaryContainer = RickTextPrimary,   // Light text on container
    
    secondary = RickActionSecondary,        // Electric purple
    onSecondary = RickTextPrimary,         // Light text on secondary
    secondaryContainer = RickSecondary,     // Deep purple container
    onSecondaryContainer = RickTextSecondary, // Muted text on container
    
    tertiary = RickAccent,                 // Portal orange
    onTertiary = RickPrimary,              // Dark text on tertiary
    
    background = RickBackground,            // Deep space background
    onBackground = RickTextPrimary,        // Light text on background
    
    surface = RickSurface,                 // Card surfaces
    onSurface = RickTextPrimary,          // Light text on surface
    surfaceVariant = RickSurfaceVariant,   // Elevated surfaces
    onSurfaceVariant = RickTextSecondary,  // Muted text on surface variant
    
    outline = RickTextTertiary,            // Borders and dividers
    outlineVariant = RickSecondary,        // Subtle borders
    
    error = RickStatusDead,                // Error states
    onError = RickTextPrimary,            // Text on error
    
    surfaceTint = RickAction               // Surface tinting
)

// Light theme for users who prefer it (optional)
private val RickMortyLightColorScheme = lightColorScheme(
    primary = RickSecondary,               // Deep purple for primary in light mode
    onPrimary = RickTextPrimary,          // Light text on primary
    primaryContainer = RickAction,         // Cyan container
    onPrimaryContainer = RickPrimary,     // Dark text on container
    
    secondary = RickActionSecondary,       // Electric purple
    onSecondary = RickTextPrimary,        // Light text on secondary
    secondaryContainer = RickSurfaceVariant, // Light container
    onSecondaryContainer = RickTextSecondary, // Muted text
    
    tertiary = RickAccent,                // Portal orange
    onTertiary = Color.White,             // White text on tertiary
    
    background = Color(0xFFF8FAFC),       // Light background
    onBackground = RickPrimary,           // Dark text on light background
    
    surface = Color.White,                // White surfaces
    onSurface = RickPrimary,             // Dark text on surface
    surfaceVariant = Color(0xFFF1F5F9),  // Light surface variant
    onSurfaceVariant = RickSecondary,     // Purple text on light surface
    
    outline = RickTextTertiary,           // Borders
    outlineVariant = Color(0xFFE2E8F0),  // Light borders
    
    error = RickStatusDead,               // Error states
    onError = Color.White,               // White text on error
    
    surfaceTint = RickAction             // Surface tinting
)

@Composable
fun MortyTheme(
    darkTheme: Boolean = true, // Default to dark theme for sci-fi aesthetic
    // Disable dynamic color to maintain Rick & Morty branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Only use dynamic colors if explicitly enabled and available
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Use Rick & Morty themed colors by default
        darkTheme -> RickMortyDarkColorScheme
        else -> RickMortyLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}