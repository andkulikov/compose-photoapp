package compose.photoapp

import androidx.compose.Composable
import androidx.compose.State
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.unit.dp
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
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.onSurface,
        contentColor = MaterialTheme.colors.surface
    ) {
        // TODO: remove Column when Surface uses it inside
        Column(Modifier.padding(top = 32.dp)) {
            Text(
                text = "Hello,",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "Alice",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 16.dp)
            )
            Card(title = "Your feed") {
                AdapterList(
                    data = photographers.collectAsState().value,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    FeedItem(photographer = it, onClick = { onSelected(it) })
                }
            }
        }
    }
}

// TODO this function is a part of dev12 release, to remove once we migrate to this version
@ExperimentalCoroutinesApi
@Composable
fun <T : Any> StateFlow<T>.collectAsState(): State<T> = composeCollectAsState(value)