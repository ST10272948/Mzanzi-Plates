package com.mzansiplatess.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.Typography


// ----------------- Orange Color Scheme -----------------
private val OrangeColorScheme = lightColorScheme(
    primary = Color(0xFFFF9800),        // Orange
    onPrimary = Color.White,
    secondary = Color(0xFFFFB74D),      // Lighter orange
    onSecondary = Color.Black,
    background = Color(0xFFFFF3E0),     // Light cream
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

// ----------------- Typography -----------------
val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)


// ----------------- App Theme -----------------
@Composable
fun MzansiPlatessTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = OrangeColorScheme,
        typography = AppTypography,
        content = content
    )
}

