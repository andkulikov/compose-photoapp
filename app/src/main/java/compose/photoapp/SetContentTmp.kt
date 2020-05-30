package compose.photoapp

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.compose.Composable
import androidx.compose.Composition
import androidx.compose.Recomposer
import androidx.ui.core.setContent

// workaround for the bug. to be removed once the fix is released
fun ComponentActivity.setContentTmp(content: @Composable() () -> Unit): Composition {
    val composeView: FixFrameLayout = window.decorView
        .findViewById<ViewGroup>(android.R.id.content)
        .getChildAt(0) as? FixFrameLayout
        ?: FixFrameLayout(this).also {
            setContentView(it)
        }
    return composeView.setContent(Recomposer.current(), content)
}

private class FixFrameLayout(context: Context): FrameLayout(context) {

    override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
        return try {
            super.dispatchTouchEvent(motionEvent)
        } catch (e: IllegalStateException) {
            Log.e("PhotoApp", "error caught", e)
            true
        }
    }
}