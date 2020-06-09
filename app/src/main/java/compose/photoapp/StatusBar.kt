package compose.photoapp

import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.ui.graphics.luminance
import androidx.ui.graphics.toArgb
import androidx.ui.material.MaterialTheme

@Composable
fun Window.updateStatusBar() {
    val color = MaterialTheme.colors.onSurface
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        onCommit(statusBarColor) {
            statusBarColor = color.toArgb()
            val isLight = color.luminance() > 0.5f
            decorView.systemUiVisibility = if (isLight) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        }
    }
}