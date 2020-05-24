package compose.photoapp

import androidx.compose.Composable
import androidx.compose.State
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.material.Surface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.collectAsState as composeCollectAsState

@ExperimentalCoroutinesApi
@Composable
fun Feed(
    photographers: StateFlow<List<Photographer>>,
    onSelected: (Photographer) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier) {
        AdapterList(data = photographers.collectAsState().value) {
            FeedItem(photographer = it, onClick = { onSelected(it) })
        }
    }
}

// TODO this function is a part of dev12 release, to remove once we migrate to this version
@ExperimentalCoroutinesApi
@Composable
fun <T : Any> StateFlow<T>.collectAsState(): State<T> = composeCollectAsState(value)