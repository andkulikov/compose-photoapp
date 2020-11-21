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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
        LazyColumn {
            item {
                FeedHeader()
            }
            items(photographers.subList(fromIndex = 0, toIndex = minOf(photographers.size, 2))) {
                PhotographerCard(photographer = it, onClick = { onSelected(it) })
            }
            if (photographers.size > 2) {
                item {
                    AdBanner()
                }
                items(photographers.subList(fromIndex = 2, toIndex = photographers.size)) {
                    PhotographerCard(photographer = it, onClick = { onSelected(it) })
                }
            }
        }
    }
}
