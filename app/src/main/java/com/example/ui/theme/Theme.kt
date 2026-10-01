package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Amber500,
    onPrimary = Zinc950,
    primaryContainer = Amber600,
    onPrimaryContainer = PureWhite,
    secondary = Amber400,
    onSecondary = Zinc950,
    secondaryContainer = Zinc800,
    onSecondaryContainer = Amber400,
    tertiary = GrillFlame,
    onTertiary = PureWhite,
    background = Zinc950,
    onBackground = Zinc100,
    surface = Zinc900,
    onSurface = Zinc100,
    surfaceVariant = Zinc800,
    onSurfaceVariant = Zinc400,
    outline = Zinc700,
    outlineVariant = Zinc800
)

// Truce Lounge is primarily an upscale evening & 24/7 night lounge.
// Even in light mode, we maintain a rich charcoal/amber aesthetic.
private val LightColorScheme = DarkColorScheme

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve brand identity
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Zinc950.toArgb()
            window.navigationBarColor = Zinc950.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
