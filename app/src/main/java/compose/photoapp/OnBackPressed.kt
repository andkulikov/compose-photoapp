package compose.photoapp

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.Composable
import androidx.compose.onActive

@Composable
fun onBackPressed(dispatcher: OnBackPressedDispatcher, callback: () -> Unit) {
    onActive {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback.invoke()
            }
        }
        dispatcher.addCallback(backPressedCallback)
        onDispose {
            backPressedCallback.remove()
        }
    }
}