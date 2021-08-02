package compose.photoapp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch

@Composable
fun TutorialOverlay(content: @Composable (Modifier) -> Unit) {
    var parentCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    var highlightCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    val animatedAlpha = remember { Animatable(0f) }
    LaunchedEffect(animatedAlpha) {
        animatedAlpha.animateTo(1f, HighlightAppearanceAnimationSpec)
    }
    val tutorialIsActive by remember(animatedAlpha) { derivedStateOf { animatedAlpha.value != 0f } }
    Box(
        Modifier.onGloballyPositioned { parentCoordinates = it }
    ) {
        content(
            Modifier
                .onGloballyPositioned { highlightCoordinates = it }
                .drawBehind {
                    if (tutorialIsActive) {
                        drawCircle(
                            Color.White,
                            alpha = animatedAlpha.value,
                            radius = size.maxDimension / 2f + HighlightExtraRadius.toPx()
                        )
                    }
                }
        )
        if (tutorialIsActive) {
            val scope = rememberCoroutineScope()
            Box(
                Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val path = calculateClipPath(highlightCoordinates, parentCoordinates)
                        onDrawBehind {
                            if (path != null && tutorialIsActive) {
                                drawPath(path, Color.Black, alpha = 0.8f * animatedAlpha.value)
                            }
                        }
                    }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        scope.launch {
                            animatedAlpha.animateTo(0f, HighlightDisappearanceAnimationSpec)
                        }
                    }
            ) {
                Text(
                    "Tap on the button to start following the artist",
                    Modifier
                        .align(Alignment.Center)
                        .padding(48.dp)
                        .graphicsLayer { alpha = animatedAlpha.value },
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }
        }
    }
}

private fun Density.calculateClipPath(
    highlight: LayoutCoordinates?,
    parent: LayoutCoordinates?
) = if (highlight != null && parent != null) {
    val highlightRect = parent.localBoundingBoxOf(highlight)
    val circleRect = Rect(
        center = highlightRect.center,
        radius = highlightRect.maxDimension / 2f + HighlightExtraRadius.toPx()
    )
    Path.combine(
        PathOperation.Difference,
        Path().apply {
            addRect(parent.size.toSize().toRect())
        },
        Path().apply {
            addOval(circleRect)
        }
    )
} else {
    null
}

private val HighlightExtraRadius = 8.dp
private val HighlightAppearanceAnimationSpec =
    tween<Float>(durationMillis = 500, delayMillis = 2000, easing = LinearOutSlowInEasing)
private val HighlightDisappearanceAnimationSpec =
    tween<Float>(durationMillis = 250, easing = FastOutLinearInEasing)

