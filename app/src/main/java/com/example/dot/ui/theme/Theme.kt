package com.example.dot.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(

    primary = Color.hsl(234f, 0.10f, 0.75f),
    background = Color.hsl(234f, 0.10f,0.25f),
    surface = Color.DarkGray,
    onPrimary = Color.White,
    onSecondary = Color.hsl(234f, 0.10f, 0.37f),
    onBackground = Color.White,
    onSurface = Color.hsl(234f, 0.10f, 0.59f),
)

private val LightColorPalette = lightColors(

    primary = Color.hsl(206f, 0.29f, 0.29f),
    background = Color.White,
    surface = Color.DarkGray,
    onPrimary =  Color.hsl(215f, 0.28f, 0.31f ),
    onSecondary = Color.hsl(206f, 0.29f, 0.80f),
    onBackground = Color.Black,
    onSurface = Color.hsl(215f, 0.29f, 0.77f, 0.3f),

)

@Composable
fun DotTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}