package com.example.zobaze.ui.theme

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

// Dark theme colors (you can refine later if you want stronger contrast)
private val DarkColorScheme = darkColorScheme(
    primary = SecondaryColor,        // Accent
    secondary = PrimaryColor,        // White as secondary
    background = Color.Black,
    surface = SurfaceGrey,
    onPrimary = Color.Black,
    onSecondary = TextPrimary,
    onBackground = Color.White,
    onSurface = Color.White,
    error = ErrorRed
)

// Light theme colors (based on your Zobase palette)
private val LightColorScheme = lightColorScheme(
    primary = SecondaryColor,        // Blue accent
    secondary = PrimaryColor,        // White
    background = BackgroundPrimary,
    surface = SurfaceWhite,
    onPrimary = PrimaryColor,        // White buttons with text
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = ErrorRed
)

@Composable
fun ZobazeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color (Material You) is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
