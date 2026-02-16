package com.kitsune.tech.library.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GoldLibrary,
    secondary = GoldDark,
    tertiary = GoldLight,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkBackground,
    onSecondary = White,
    onTertiary = DarkBackground,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = GoldLibrary,
    secondary = GoldDark,
    tertiary = GoldLight,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkBackground,
    onSecondary = White,
    onTertiary = DarkBackground,
    onBackground = White,
    onSurface = White
)

@Composable
fun LibraryTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}