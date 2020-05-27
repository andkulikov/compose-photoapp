package compose.photoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.Providers
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.animation.Crossfade
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
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
