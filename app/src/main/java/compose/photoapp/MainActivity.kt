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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideAdProvider {
                PhotoAppTheme {
                    val viewModel = viewModel<PhotographersViewModel>()
                    var selectedId by savedInstanceState<String?> { null }
                    Crossfade(current = selectedId) { id ->
                        if (id == null) {
                            Feed(
                                viewModel.photographers,
                                onSelected = { selectedId = it.id }
                            )
                        } else {
                            Profile(viewModel.getById(id))
                            onBackPressed(onBackPressedDispatcher) {
                                selectedId = null
                            }
                        }
                    }
                    window.updateStatusBar()
                }
            }
        }
    }
}
