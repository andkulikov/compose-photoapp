package compose.photoapp

import androidx.animation.PhysicsBuilder
import androidx.animation.Spring
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.animation.PxPropKey
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
import androidx.ui.unit.dp
import androidx.ui.unit.toPx

@Composable
fun PhotosTab(groups: List<String>, selectedGroup: String, onSelected: (String) -> Unit) {
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

@Preview
@Composable
fun TabPreview() {
    MaterialTheme {
        PhotosTab(
            groups = listOf("sports", "portrait", "b/w", "neon city"),
            selectedGroup = "b/w",
            onSelected = {}
        )
    }
}