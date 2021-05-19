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

@file:OptIn(ExperimentalAnimationApi::class)

package compose.photoapp

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun FeedFab(state: LazyListState, modifier: Modifier) {
    AnimatedVisibility(
        visible = state.isScrollingUp,
        modifier = modifier,
        enter = FabEnterAnim,
        exit = FabExitAnim,
    ) {
        val context = LocalContext.current
        FloatingActionButton(
            onClick = {
                Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.padding(8.dp),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(Icons.Default.Add, "Add")
        }
    }
}

private val LazyListState.isScrollingUp: Boolean
    @Composable
    get() {
        return remember {
            var lastIndex by mutableStateOf(0)
            var lastScroll by mutableStateOf(Int.MAX_VALUE)
            derivedStateOf {
                val currentIndex = firstVisibleItemIndex
                val currentScroll = firstVisibleItemScrollOffset
                val scrollingUp = currentIndex < lastIndex ||
                        (currentIndex == lastIndex && currentScroll < lastScroll)
                if (currentIndex != lastIndex || currentScroll != lastScroll) {
                    lastIndex = currentIndex
                    lastScroll = currentScroll
                }
                scrollingUp
            }
        }.value
    }

private val FabEnterAnim = slideInVertically(initialOffsetY = { it })
private val FabExitAnim = slideOutVertically(targetOffsetY = { it })
