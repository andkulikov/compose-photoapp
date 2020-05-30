package compose.photoapp

import androidx.compose.Composable
import androidx.ui.animation.Crossfade

// workaround for the bug in compose until the fix will be released
@Composable
fun <T> CrossfadeTmp(
    current: T?,
    children: @Composable() (T?) -> Unit
) {
    Crossfade(current = current ?: "null") {
        @Suppress("UNCHECKED_CAST")
        children(if (it == "null") null else it as T)
    }
}