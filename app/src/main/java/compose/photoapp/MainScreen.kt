/*
 * Copyright 2021 The Android Open Source Project
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

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    StatusBarColorProvider()
    Surface(color = MaterialTheme.colors.onSurface) {
        val viewModel = viewModel<PhotographersViewModel>()
        var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
        Crossfade(targetState = selectedId) { id ->
            if (id == null) {
                Feed(
                    viewModel.photographers,
                    onSelected = { selectedId = it.id }
                )
            } else {
                Profile(viewModel.getById(id))
                BackHandler {
                    selectedId = null
                }
            }
        }
    }
}
