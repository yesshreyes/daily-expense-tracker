package com.example.zobaze.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

/**
 * Color palette for Zobase Expense Tracker
 * Modern design with glassmorphism effects
 */

// Primary Colors
val PrimaryColor = Color.White
val SecondaryColor = Color(0xFF476EE4)

// Glass and Transparency Effects
val GlassColor = Color(0x80FFFFFF) // 50% transparent white
val GlassColorLight = Color(0x60FFFFFF) // 37% transparent white
val GlassColorDark = Color(0xA0FFFFFF) // 63% transparent white

// Background Colors
val BackgroundPrimary = Color(0xFFF8FAFF)
val BackgroundSecondary = Color(0xFFE8F2FF)

// Gradient Backgrounds
val BackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        BackgroundPrimary,
        BackgroundSecondary
    )
)

val CardGradient = Brush.linearGradient(
    colors = listOf(
        GlassColor,
        GlassColorLight
    )
)

// Text Colors
val TextPrimary = Color(0xFF333333)
val TextSecondary = Color(0xFF666666)
val TextTertiary = Color(0xFF888888)
val TextHint = Color(0xFF999999)

// Surface Colors
val SurfaceWhite = Color.White
val SurfaceGrey = Color(0xFFF5F5F5)
val SurfaceGreyLight = Color(0xFFF8F8F8)

// Border Colors
val BorderLight = Color(0xFFE0E0E0)
val BorderMedium = Color(0xFFCCCCCC)
val BorderFocused = SecondaryColor

// Shadow Colors
val ShadowLight = SecondaryColor.copy(alpha = 0.08f)
val ShadowMedium = SecondaryColor.copy(alpha = 0.15f)
val ShadowDark = SecondaryColor.copy(alpha = 0.25f)

// Status Colors
val SuccessGreen = Color(0xFF4CAF50)
val ErrorRed = Color(0xFFF44336)
val WarningOrange = Color(0xFFFF9800)
val InfoBlue = Color(0xFF2196F3)

// Category Colors (for expense categories)
val CategoryStaff = Color(0xFF9C27B0)
val CategoryTravel = Color(0xFF00BCD4)
val CategoryFood = Color(0xFF4CAF50)
val CategoryUtility = Color(0xFFFF9800)

// Transparent Colors
val Transparent = Color.Transparent
val BlackOverlay = Color(0x40000000) // 25% black overlay
val WhiteOverlay = Color(0x40FFFFFF) // 25% white overlay

/**
 * Extension functions for common color variations
 */

// Alpha variations
fun Color.withAlpha(alpha: Float): Color = this.copy(alpha = alpha)

// Lighter and darker variations
fun Color.lighter(factor: Float = 0.2f): Color {
    return Color(
        red = (red + (1 - red) * factor).coerceIn(0f, 1f),
        green = (green + (1 - green) * factor).coerceIn(0f, 1f),
        blue = (blue + (1 - blue) * factor).coerceIn(0f, 1f),
        alpha = alpha
    )
}

fun Color.darker(factor: Float = 0.2f): Color {
    return Color(
        red = (red * (1 - factor)).coerceIn(0f, 1f),
        green = (green * (1 - factor)).coerceIn(0f, 1f),
        blue = (blue * (1 - factor)).coerceIn(0f, 1f),
        alpha = alpha
    )
}

/**
 * Color scheme object for easy access
 */
object ZobaseColors {
    val primary = PrimaryColor
    val secondary = SecondaryColor
    val background = BackgroundGradient
    val surface = SurfaceWhite
    val glass = GlassColor
    val textPrimary = TextPrimary
    val textSecondary = TextSecondary
    val border = BorderLight
    val shadow = ShadowMedium
}