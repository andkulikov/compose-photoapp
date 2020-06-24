package compose.photoapp

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

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

@Preview
@Composable
fun PhotoAppThemePreview() {
    PhotoAppTheme {
        Surface {
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Demo")
            }
        }
    }
}