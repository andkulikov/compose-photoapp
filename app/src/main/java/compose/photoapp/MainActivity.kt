package compose.photoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.viewmodel.viewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideAdProvider {
                PhotoAppTheme {
                    val viewModel = viewModel<PhotographersViewModel>()
                    var selectedId by savedInstanceState<String?> { null }
                    Crossfade(current = selectedId) { id ->
                        if (id == null) {
                            Feed(
                                viewModel.photographers,
                                onSelected = { selectedId = it.id }
                            )
                        } else {
                            Profile(viewModel.getById(id))
                            onBackPressed(onBackPressedDispatcher) {
                                selectedId = null
                            }
                        }
                    }
                    window.updateStatusBar()
                }
            }
        }
    }
}
