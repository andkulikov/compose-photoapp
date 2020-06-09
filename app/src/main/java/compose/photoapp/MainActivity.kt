package compose.photoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.Providers
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.savedinstancestate.savedInstanceState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<PhotographersViewModel>()
        val adProvider = AdProvider(this, lifecycle)
        setContentTmp {
            Providers(AdProviderAmbient provides adProvider) {
                PhotoAppTheme {
                    var selectedId by savedInstanceState<String?> { null }
                    CrossfadeTmp(current = selectedId) { id ->
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
