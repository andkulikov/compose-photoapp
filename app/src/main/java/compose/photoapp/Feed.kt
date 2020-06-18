package compose.photoapp

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.getValue
import androidx.ui.core.Modifier
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.Surface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
@Composable
fun Feed(
    photographersFlow: StateFlow<List<Photographer>>,
    onSelected: (Photographer) -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        val photographers by photographersFlow.collectAsState()
        LazyColumnItems(
            mutableListOf<FeedItem>().apply {
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
}

private sealed class FeedItem {
    object Header : FeedItem()
    data class PhotographerCard(val photographer: Photographer) : FeedItem()
    object Ad : FeedItem()
}
