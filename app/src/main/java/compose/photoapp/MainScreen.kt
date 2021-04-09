package compose.photoapp

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    StatusBarColorProvider()
    Surface(color = MaterialTheme.colors.onSurface) {
        val viewModel = viewModel<PhotographersViewModel>()
        var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
        Crossfade(targetState = selectedId) { id ->
            if (id == null) {
                Feed(
                    viewModel.photographers,
                    onSelected = { selectedId = it.id }
                )
            } else {
                Profile(viewModel.getById(id))
                BackHandler {
                    selectedId = null
                }
            }
        }
    }
}
