package compose.photoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.Providers
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.core.setContent
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
                PhotoAppTheme {
                    var selectedId by savedInstanceState<String?> { null }
                    if (selectedId == null) {
                        Feed(
                            viewModel.photographers,
                            onSelected = { selectedId = it.id }
                        )
                    } else {
                        Profile(viewModel.getById(selectedId!!))
                        onBackPressed(onBackPressedDispatcher) {
                            selectedId = null
                        }
                    }
                }
            }
        }
    }
}
