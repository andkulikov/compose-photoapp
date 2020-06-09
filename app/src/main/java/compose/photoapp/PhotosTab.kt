package compose.photoapp

import androidx.animation.Spring
import androidx.animation.transitionDefinition
import androidx.compose.*
import androidx.ui.animation.DpPropKey
import androidx.ui.animation.Transition
import androidx.ui.animation.animate
import androidx.ui.core.Alignment
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Canvas
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Tab
import androidx.ui.material.TabRow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.*

@Composable
fun PhotosTab(groups: List<String>, selectedGroup: String, onSelected: (String) -> Unit) {
    TabRow(
        items = groups,
        selectedIndex = groups.indexOf(selectedGroup),
        backgroundColor = MaterialTheme.colors.surface,
        indicatorContainer = { positions ->
            TabIndicatorContainer(positions.convertToDp(), groups.indexOf(selectedGroup)) {
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
                EmphasisAmbient.current.disabled.applyEmphasis(MaterialTheme.colors.onSurface)
        )
        Tab(
            text = { Text(text = group, color = color) },
            selected = index == groups.indexOf(selectedGroup),
            onSelected = { onSelected(group) },
            activeColor = MaterialTheme.colors.surface
        )
    }
}


@Composable
private fun TabIndicatorContainer(
    tabPositions: List<TabPosition>,
    selectedIndex: Int,
    indicator: @Composable() () -> Unit
) {
    val indicatorOffset = remember { DpPropKey() }

    val transitionDefinition = remember(tabPositions) {
        transitionDefinition {
            tabPositions.forEachIndexed { index, position ->
                state(index) {
                    this[indicatorOffset] = (position.left + position.right) / 2
                }
            }
            transition {
                indicatorOffset using physics<Dp> {
                    dampingRatio = Spring.DampingRatioLowBouncy
                    stiffness = Spring.StiffnessLow
                }
            }
        }
    }

    Transition(transitionDefinition, selectedIndex, initState = selectedIndex) { state ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.BottomStart)
                .offset(x = state[indicatorOffset], y = (-2).dp),
            children = indicator
        )
    }
}

@Preview
@Composable
fun TabPreview() {
    MaterialTheme {
        var selectedGroup by state { "b/w" }
        PhotosTab(
            groups = listOf("sports", "portrait", "b/w", "neon city"),
            selectedGroup = selectedGroup,
            onSelected = { selectedGroup = it }
        )
    }
}

// temporary until we migrate to the latest Compose build where it is already in dp
@Composable
fun List<TabRow.TabPosition>.convertToDp(): List<TabPosition> {
    return with(DensityAmbient.current) {
        map { TabPosition(it.left.toDp(), it.width.toDp()) }
    }
}

data class TabPosition internal constructor(val left: Dp, val width: Dp) {
    val right: Dp get() = left + width
}