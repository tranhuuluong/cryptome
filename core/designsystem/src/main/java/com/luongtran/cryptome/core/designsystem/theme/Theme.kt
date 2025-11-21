package com.luongtran.cryptome.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = BlueBrand,
    onPrimary = White,
    primaryContainer = BlueBrand,
    onPrimaryContainer = White,
    secondary = Color(0xFF1976D2),
    onSecondary = White,
    secondaryContainer = Color(0xFF42A5F5),
    onSecondaryContainer = Color(0xFF0A1228),
    tertiary = Color(0xFF00BFFF),
    onTertiary = Color(0xFF0A1228),
    tertiaryContainer = Color(0xFF80D8FF),
    onTertiaryContainer = Color(0xFF0A1228),
    background = White,
    onBackground = Color(0xFF0A1228),
    surface = White,
    onSurface = Color(0xFF0A1228),
    surfaceVariant = Color(0xFFE1E6F0),
    onSurfaceVariant = Color(0xFF576A8C),
    inverseSurface = BlueBrand,
    inverseOnSurface = White,
    error = Color(0xFFF44336),
    onError = White,
    errorContainer = Color(0xFFD32F2F),
    onErrorContainer = White,
    outline = Color(0xFF8C9DB9),
    outlineVariant = Color(0xFFC7D0E0)
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = White,
    primaryContainer = BlueBrand,
    onPrimaryContainer = White,
    secondary = Color(0xFF42A5F5),
    onSecondary = BlueDarkBrand,
    secondaryContainer = Color(0xFF1976D2),
    onSecondaryContainer = White,
    tertiary = Color(0xFF80D8FF),
    onTertiary = BlueDarkBrand,
    tertiaryContainer = Color(0xFF00BFFF),
    onTertiaryContainer = White,
    background = BlueDarkBrand,
    onBackground = White,
    surface = Color(0xFF112B4A),
    onSurface = White,
    surfaceVariant = Color(0xFF0D3C73),
    onSurfaceVariant = Color(0xFF8C9DB9),
    inverseSurface = White,
    inverseOnSurface = BlueDarkBrand,
    error = Color(0xFFF44336),
    onError = White,
    errorContainer = Color(0xFFD32F2F),
    onErrorContainer = White,
    outline = Color(0xFF576A8C),
    outlineVariant = Color(0xFF435373)
)


@Composable
fun CryptomeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}