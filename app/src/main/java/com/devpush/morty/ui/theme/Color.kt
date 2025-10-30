package com.devpush.morty.ui.theme

import androidx.compose.ui.graphics.Color

// Material 3 Base Colors
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Rick & Morty Fresh Theme - Inspired by interdimensional sci-fi aesthetics
// Primary Colors - Deep space with neon accents
val RickPrimary = Color(0xFF0A0E1A)           // Deep space navy - main background
val RickPrimaryVariant = Color(0xFF1A1F2E)    // Lighter variant for cards/surfaces
val RickSecondary = Color(0xFF2D1B69)         // Deep purple for secondary elements

// Accent Colors - Vibrant sci-fi neons
val RickAction = Color(0xFF00D4AA)            // Bright cyan-green - primary actions
val RickActionSecondary = Color(0xFF7C4DFF)   // Electric purple - secondary actions
val RickAccent = Color(0xFFFF6B35)           // Portal orange - highlights/warnings

// Surface Colors - Modern glass morphism
val RickSurface = Color(0xFF151B2D)           // Card backgrounds
val RickSurfaceVariant = Color(0xFF1E2538)    // Elevated surfaces
val RickBackground = Color(0xFF0F1419)        // App background

// Text Colors - High contrast for accessibility
val RickTextPrimary = Color(0xFFE8F4F8)       // Primary text - off-white with blue tint
val RickTextSecondary = Color(0xFFB0BEC5)     // Secondary text - muted blue-gray
val RickTextTertiary = Color(0xFF78909C)      // Tertiary text - subtle gray

// Status Colors - Character status indicators
val RickStatusAlive = Color(0xFF4CAF50)       // Green for alive
val RickStatusDead = Color(0xFFE53E3E)        // Red for dead
val RickStatusUnknown = Color(0xFFFF9800)     // Orange for unknown

// Gradient Colors - For modern effects
val RickGradientStart = Color(0xFF1A237E)     // Deep blue
val RickGradientEnd = Color(0xFF3949AB)       // Lighter blue
val RickGradientAccent = Color(0xFF00BCD4)    // Cyan accent

// Legacy colors for backward compatibility (mapped to new theme)
@Deprecated("Use RickSurface instead", ReplaceWith("RickSurface"))
val RickSurfaceOld = Color(0xffead7ce)