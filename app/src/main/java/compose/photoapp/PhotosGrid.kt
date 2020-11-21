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

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

@Composable
fun PhotosGrid(images: List<Int>, modifier: Modifier = Modifier) {
    Layout(
        content = {
            require(images.size >= 6) { "Requires 6 photos for the grid." }
            images.subList(0, 6).forEach {
                FadeInImage(it, Modifier.aspectRatio(1f).clip(RoundedCornerShape(16.dp)))
            }
        },
        modifier = modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
    ) { measurables, constraints ->
        val padding = 8.dp.toIntPx()

        // to cover small screens
        val minDimension = minOf(constraints.maxHeight, constraints.maxWidth)

        val smallImageConstraints = constraints.copy(
            minWidth = (minDimension - padding * 2) / 3,
            maxWidth = (minDimension - padding * 2) / 3
        )
        val placeables = measurables
            .subList(fromIndex = 1, toIndex = measurables.size)
            .map {
                it.measure(smallImageConstraints)
            }

        val bigImageConstraints = constraints.copy(
            minWidth = minDimension - padding - placeables[0].width,
            maxWidth = minDimension - padding - placeables[0].width
        )
        val bigImagePlaceable = measurables.first().measure(bigImageConstraints)

        // calculate size of the layout
        val height = placeables[0].height * 3 + padding * 2
        val width = placeables[0].width * 3 + padding * 2

        layout(width, height) {
            var positionX = 0
            var positionY = 0

            bigImagePlaceable.place(positionX, positionY)

            placeables.forEachIndexed { index, placeable ->
                if (index < 2) {
                    // to the right from the big image
                    placeable.place(bigImagePlaceable.width + padding, positionY)
                    positionY += placeable.height + padding
                } else {
                    // bottom row
                    placeable.place(positionX, positionY)
                    positionX += placeable.width + padding
                }
            }
        }
    }
}
