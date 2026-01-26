package com.duoc.ecorentfinal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),           // Verde principal (oscuro para contraste)
    secondary = Color(0xFF4FC3F7),         // Azul claro pastel
    tertiary = Color(0xFFFFB74D),          // Naranja pastel
    background = Color(0xFFF0F9F0),        // Verde menta MUY suave (95% blanco)
    surface = Color(0xFFFFFFFF),           // Blanco puro
    onBackground = Color(0xFF1B5E20),      // Verde oscuro para texto
    onSurface = Color(0xFF000000)          // Negro para texto
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),           // Verde pastel claro
    secondary = Color(0xFF64B5F6),         // Azul pastel
    tertiary = Color(0xFFFFCC80),          // Naranja pastel
    background = Color(0xFF121212),        // Negro elegante
    surface = Color(0xFF1E1E1E),           // Gris oscuro
    onBackground = Color(0xFFE8F5E9),      // Verde claro pastel para texto
    onSurface = Color(0xFFFFFFFF)          // Blanco para texto
)

@Composable
fun EcoRentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}

val MaterialTheme.successGreen: Color
    get() = SuccessGreen