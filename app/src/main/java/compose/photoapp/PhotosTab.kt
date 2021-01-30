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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun PhotosTab(groups: List<String>, selectedGroup: String, onSelected: (String) -> Unit) {
    val selectedIndex = groups.indexOf(selectedGroup)
    TabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = MaterialTheme.colors.surface,
        indicator = { positions ->
            TabIndicatorContainer(positions, groups.indexOf(selectedGroup)) {
                // circle indicator
                val color = MaterialTheme.colors.primary
                Canvas(Modifier.preferredSize(4.dp)) {
                    drawCircle(color)
                }
            }
        },
        divider = {}
    ) {
        groups.forEachIndexed { index, group ->
            val color = animateColorAsState(
                if (selectedGroup == group) MaterialTheme.colors.primary else
                    MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            )
            Tab(
                selected = index == selectedIndex,
                text = { Text(text = group, color = color.value) },
                onClick = { onSelected(group) },
                selectedContentColor = MaterialTheme.colors.surface
            )
        }
    }
}

@Composable
private fun TabIndicatorContainer(
    tabPositions: List<TabPosition>,
    selectedIndex: Int,
    content: @Composable() () -> Unit
) {
    val transition = updateTransition(targetState = selectedIndex)

    val offset = transition.animateDp(transitionSpec = {
        spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
    }) {
        val position = tabPositions[it]
        (position.left + position.right) / 2
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomStart)
            .offset { IntOffset(x = offset.value.toIntPx(), y = (-2).dp.toIntPx()) }
    ) {
        content()
    }
}

@Preview
@Composable
fun TabPreview() {
    PhotoAppTheme {
        var selectedGroup by remember { mutableStateOf("b/w") }
        PhotosTab(
            groups = listOf("sports", "portrait", "b/w", "neon city"),
            selectedGroup = selectedGroup,
            onSelected = { selectedGroup = it }
        )
    }
}
