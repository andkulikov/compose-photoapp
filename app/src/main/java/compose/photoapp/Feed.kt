/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
