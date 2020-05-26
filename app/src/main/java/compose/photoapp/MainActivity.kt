package compose.photoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.Providers
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.layout.Column
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.savedinstancestate.savedInstanceState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<PhotographersViewModel>()
        val adProvider = AdProvider(this, lifecycle)
        setContent {
            Providers(AdProviderAmbient provides adProvider) {
                MaterialTheme {
                    Surface {
                        Column {
                            var selectedId by savedInstanceState<String?> { null }
                            val selected = viewModel.getById(selectedId)
                            if (selected == null) {
                                Feed(
                                    viewModel.photographers,
                                    onSelected = { selectedId = it.id }
                                )
                            } else {
                                Profile(selected)
                                onBackPressed(onBackPressedDispatcher) {
                                    selectedId = null
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
