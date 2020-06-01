package compose.photoapp

import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

@Composable
fun PhotoAppTheme(content: @Composable() () -> Unit) {
    val primary = Color(0xffd32f2f)
    val lightColors = lightColorPalette(
        primary = primary
    )
    val darkColors = darkColorPalette(
        primary = primary,
        onPrimary = Color.White
    )
    val colors = if (isSystemInDarkTheme()) darkColors else lightColors
    MaterialTheme(colors = colors, content = content)
}
