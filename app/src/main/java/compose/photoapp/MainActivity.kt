package compose.photoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.onActive
import androidx.compose.setValue
import androidx.ui.animation.Crossfade
import androidx.ui.core.*
import androidx.ui.foundation.drawBackground
import androidx.ui.graphics.RectangleShape
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.viewinterop.AndroidView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<PhotographersViewModel>()
        val adView = createAdView(this)
        setContent {
            MaterialTheme {
                Column {
                    var selectedId by savedInstanceState<String?> { null }
                    val selected = viewModel.getById(selectedId)
                    val modifier = Modifier.weight(1f)
                    if (selected == null) {
                        Feed(
                            viewModel.photographers,
                            onSelected = { selectedId = it.id },
                            modifier = modifier
                        )
                    } else {
                        Profile(selected, modifier)
                        onBackPressed(onBackPressedDispatcher) {
                            selectedId = null
                        }
                    }
                    AndroidView(adView)
                }
            }
        }
    }
}
