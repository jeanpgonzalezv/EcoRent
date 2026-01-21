package com.duoc.ecorentfinal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LightGreen,
    secondary = OceanBlue,
    tertiary = EnergeticOrange,
    background = Color(0xFF0A1F0A),
    surface = Color(0xFF1A3C1A)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = OceanBlue,
    tertiary = EnergeticOrange,
    background =  Color(0xFFF0F8F0),
    surface = Color.White
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