package compose.photoapp

import androidx.compose.Composable
import androidx.compose.State
import androidx.ui.foundation.AdapterList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.collectAsState as composeCollectAsState

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
@Composable
fun Feed(
    photographersFlow: StateFlow<List<Photographer>>,
    onSelected: (Photographer) -> Unit
) {
    val photographers = photographersFlow.collectAsState().value
    AdapterList(
        buildList {
            add(FeedItem.Header)
            addAll(photographers.map { FeedItem.PhotographerCard(it) })
            if (size > 3) {
                add(3, FeedItem.Ad)
            }
        }
    ) {
        when (it) {
            is FeedItem.Header -> FeedHeader()
            is FeedItem.Ad -> AdBanner()
            is FeedItem.PhotographerCard ->
                PhotographerCard(
                    photographer = it.photographer,
                    onClick = { onSelected(it.photographer) })

        }
    }
}

private sealed class FeedItem {
    object Header : FeedItem()
    data class PhotographerCard(val photographer: Photographer) : FeedItem()
    object Ad : FeedItem()
}

// TODO this function is a part of dev12 release, to remove once we migrate to this version
@ExperimentalCoroutinesApi
@Composable
fun <T : Any> StateFlow<T>.collectAsState(): State<T> = composeCollectAsState(value)