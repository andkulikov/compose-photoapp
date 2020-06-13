package compose.photoapp

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Layout
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.aspectRatio
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.wrapContentWidth
import androidx.ui.unit.dp

@Composable
fun PhotosGrid(images: List<Int>, modifier: Modifier = Modifier) {
    Layout(
        children = {
            require(images.size >= 6) { "Requires 6 photos for the grid." }
            images.subList(0, 6).forEach {
                FadeInImage(it, Modifier.aspectRatio(1f).clip(RoundedCornerShape(16.dp)))
            }
        },
        modifier = modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
    ) { measurables, constraints, _ ->
        val padding = 8.dp.toIntPx()

        // to cover small screens
        val minDimension = minOf(constraints.maxHeight, constraints.maxWidth)

        val smallImageConstraints = constraints.copy(
            minWidth = 0,
            maxWidth = (minDimension - padding * 2) / 3
        )
        val placeables = measurables
            .subList(fromIndex = 1, toIndex = measurables.size)
            .map {
                it.measure(smallImageConstraints)
            }

        val bigImageConstraints = constraints.copy(
            minWidth = 0,
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
