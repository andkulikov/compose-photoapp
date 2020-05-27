package compose.photoapp

import androidx.animation.PhysicsBuilder
import androidx.animation.Spring
import androidx.animation.transitionDefinition
import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.animation.PxPropKey
import androidx.ui.animation.Transition
import androidx.ui.animation.animate
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.res.loadImageResource
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.*

@Composable
fun Profile(photographer: Photographer, modifier: Modifier = Modifier) {
    val padding = 16.dp
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.onSurface,
        contentColor = MaterialTheme.colors.surface
    ) {
        // TODO: remove Column when Surface uses it inside
        Column(Modifier.padding(top = 24.dp)) {
            Header(photographer)
            Spacer(modifier = Modifier.weight(1f))
            TagsList(
                photographer.tags,
                Modifier.padding(top = padding, bottom = padding)
            )
            PortfolioCard(groupedPhotos = photographer.photos)
        }
    }
}

@Composable
private fun Header(photographer: Photographer) {
    val padding = 16.dp

    Row(
        modifier = Modifier.fillMaxWidth().padding(start = padding, end = padding),
        verticalGravity = Alignment.CenterVertically
    ) {
        Image(
            asset = imageResource(id = photographer.avatar),
            modifier = Modifier.size(72.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.size(padding))
        Column {
            Text(
                text = photographer.name,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalGravity = Alignment.CenterVertically
            ) {
                FollowerInfo(text = "followers", number = photographer.numOfFollowers)
                FollowerInfo(text = "following", number = photographer.numOfFollowing)
                Button(onClick = {}, shape = CircleShape) {
                    Text("Follow")
                }
            }
        }
    }
}

@Composable
private fun FollowerInfo(text: String, number: String) {
    Column(horizontalGravity = Alignment.CenterHorizontally) {
        Text(text = number, style = MaterialTheme.typography.subtitle2)
        Text(text = text, style = MaterialTheme.typography.caption)
    }
}

@Composable
private fun TagsList(tags: List<String>, modifier: Modifier = Modifier) {
    HorizontalScroller(modifier = modifier) {
        val padding = 8.dp
        Row(Modifier.padding(start = padding * 2, end = padding)) {
            tags.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .drawBorder(
                            1.dp,
                            EmphasisAmbient.current.disabled.emphasize(contentColor()),
                            CircleShape
                        )
                        .padding(padding)
                )
                Spacer(Modifier.size(padding))
            }
        }
    }
}

@Composable
private fun PortfolioCard(groupedPhotos: Map<String, List<Int>>) {
    val groups = groupedPhotos.keys.toList()
    RoundedHeader(title = "Portfolio")
    Surface {
        Column {
            var selectedGroup by savedInstanceState { groups.first() }
            Tab(
                groups = groups,
                selectedGroup = selectedGroup,
                onSelected = { selectedGroup = it }
            )
            PhotosGrid(
                groupedPhotos.getValue(selectedGroup),
                Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun Tab(groups: List<String>, selectedGroup: String, onSelected: (String) -> Unit) {
    TabRow(
        items = groups,
        selectedIndex = groups.indexOf(selectedGroup),
        backgroundColor = MaterialTheme.colors.surface,
        indicatorContainer = { positions ->
            TabIndicatorContainer(positions, groups.indexOf(selectedGroup)) {
                // circle indicator
                val color = MaterialTheme.colors.primary
                Canvas(Modifier.preferredSize(4.dp)) {
                    drawCircle(color)
                }
            }
        },
        divider = {}
    ) { index, group ->
        val color = animate(
            if (selectedGroup == group) MaterialTheme.colors.primary else
                EmphasisAmbient.current.disabled.emphasize(MaterialTheme.colors.onSurface)
        )
        Tab(
            text = { Text(text = group, color = color) },
            selected = index == groups.indexOf(selectedGroup),
            onSelected = { onSelected(group) },
            activeColor = MaterialTheme.colors.surface
        )
    }
}

private val IndicatorOffset = PxPropKey()

@Composable
private fun TabIndicatorContainer(
    tabPositions: List<TabRow.TabPosition>,
    selectedIndex: Int,
    indicator: @Composable() () -> Unit
) {
    val transitionDefinition = remember(tabPositions) {
        transitionDefinition {
            tabPositions.forEachIndexed { index, position ->
                state(index) {
                    this[IndicatorOffset] = position.left.toPx()
                }
            }
            transition {
                IndicatorOffset using PhysicsBuilder(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            }
        }
    }

    Transition(transitionDefinition, selectedIndex, initState = selectedIndex) { state ->
        val offset = with(DensityAmbient.current) { state[IndicatorOffset].toDp() }
        val currentTabWidth = with(DensityAmbient.current) {
            tabPositions[selectedIndex].width.toDp()
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .preferredWidth(currentTabWidth)
                .wrapContentSize(Alignment.BottomCenter)
                .offset(x = offset, y = (-2).dp),
            children = indicator
        )
    }
}

@Composable
private fun PhotosGrid(images: List<Int>, modifier: Modifier = Modifier) {
    Layout(
        children = {
            require(images.size >= 6) { "Requires 6 photos for the grid." }
            images.forEach {
                FadeInImage(it, Modifier.aspectRatio(1f).clip(RoundedCornerShape(16.dp)))
            }
        },
        modifier = modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
    ) { measurables, constraints, _ ->
        val padding = 8.dp.toIntPx()

        // to cover small screens
        val minDimension = min(constraints.maxHeight, constraints.maxWidth)

        val smallImageConstraints = constraints.copy(
            minWidth = 0.ipx,
            maxWidth = (minDimension - padding * 2) / 3
        )
        val placeables = measurables
            .subList(fromIndex = 1, toIndex = measurables.size)
            .map {
                it.measure(smallImageConstraints)
            }

        val bigImageConstraints = constraints.copy(
            minWidth = 0.ipx,
            maxWidth = minDimension - padding - placeables[0].width
        )
        val bigImagePlaceable = measurables.first().measure(bigImageConstraints)

        // calculate size of the layout
        val height = placeables[0].height * 3 + padding * 2
        val width = placeables[0].width * 3 + padding * 2

        layout(width, height) {
            var positionX = 0.ipx
            var positionY = 0.ipx

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

@Preview
@Composable
fun TagPreview() {
    val demoPhotographer = Photographer(
        "id",
        "Patricia Stevenson",
        "3 minutes ago",
        R.drawable.ava1,
        R.drawable.image1,
        "101k",
        "894",
        listOf("travel", "urban", "fashion", "food"),
        emptyMap()
    )
    MaterialTheme {
        TagsList(demoPhotographer.tags)
    }
}

@Preview
@Composable
fun TabPreview() {
    MaterialTheme {
        Tab(
            groups = listOf("sports", "portrait", "b/w", "neon city"),
            selectedGroup = "b/w",
            onSelected = {}
        )
    }
}