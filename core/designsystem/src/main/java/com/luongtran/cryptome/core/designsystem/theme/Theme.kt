package com.luongtran.cryptome.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = BlueBrand,
    onPrimaryContainer = White,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = White,
    tertiary = AccentBlue,
    onTertiary = White,
    tertiaryContainer = AccentBlue,
    background = BlueDarkBrand,
    onBackground = TextPrimaryDark,
    surface = Color(0xFF071F42),
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF0A244A),
    onSurfaceVariant = TextSecondaryDark,
    inverseSurface = Color(0xFF113065),
    inverseOnSurface = TextPrimaryDark,
    outline = Neutral700,
    outlineVariant = Neutral800,
    error = ErrorMain,
    onError = OnError,
    errorContainer = ErrorVariant,
    onErrorContainer = White
)

val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = BlueBrand,
    onPrimaryContainer = White,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = White,
    tertiary = AccentBlue,
    onTertiary = White,
    tertiaryContainer = AccentBlue,
    background = Color(0xFFEFF4FB),
    onBackground = Color(0xFF0A1228),
    surface = White,
    onSurface = Color(0xFF0A1228),
    surfaceVariant = Neutral100,
    onSurfaceVariant = Neutral500,
    inverseSurface = BlueBrand,
    inverseOnSurface = White,
    outline = Neutral400,
    outlineVariant = Neutral200,
    error = ErrorMain,
    onError = White,
    errorContainer = ErrorVariant,
    onErrorContainer = White
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