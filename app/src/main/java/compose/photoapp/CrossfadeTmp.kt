package compose.photoapp

import androidx.animation.AnimatedFloat
import androidx.animation.AnimationBuilder
import androidx.animation.AnimationEndReason
import androidx.animation.TweenBuilder
import androidx.compose.*
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.layout.Stack
import androidx.ui.util.fastForEach

// Copy of the Crossfade component which only animates the new item on top of the previous one
// instead of animating both of them. it looks better when the items are fully opaque. This will
// be a second optional mode of Crossfade in the future versions of Compose
@Composable
fun <T> Crossfade(
    current: T,
    animation: AnimationBuilder<Float> = TweenBuilder(),
    opaqueChildren: Boolean = false,
    children: @Composable() (T) -> Unit
) {
    val state = remember { CrossfadeState<T>() }
    if (current != state.current) {
        state.current = current
        val keys = state.items.map { it.key }.toMutableList()
        if (!keys.contains(current)) {
            keys.add(current)
        }
        state.items.clear()
        keys.mapTo(state.items) { key ->
            CrossfadeAnimationItem(key) { children ->
                val opacity = animatedOpacity(
                    animation = animation,
                    visible = key == current,
                    opaqueChildren = opaqueChildren,
                    onAnimationFinish = {
                        if (key == state.current) {
                            // leave only the current in the list
                            state.items.removeAll { it.key != state.current }
                            state.invalidate()
                        }
                    }
                )
                Stack(Modifier.drawOpacity(opacity.value)) {
                    children()
                }
            }
        }
    }
    Stack {
        state.invalidate = invalidate
        state.items.fastForEach { (item, opacity) ->
            key(item) {
                opacity {
                    children(item)
                }
            }
        }
    }
}

private class CrossfadeState<T> {
    // we use Any here as something which will not be equals to the real initial value
    var current: Any? = Any()
    var items = mutableListOf<CrossfadeAnimationItem<T>>()
    var invalidate: () -> Unit = { }
}

private data class CrossfadeAnimationItem<T>(
    val key: T,
    val transition: CrossfadeTransition
)

private typealias CrossfadeTransition = @Composable() (children: @Composable() () -> Unit) -> Unit

@Composable
private fun animatedOpacity(
    animation: AnimationBuilder<Float>,
    visible: Boolean,
    opaqueChildren: Boolean,
    onAnimationFinish: () -> Unit = {}
): AnimatedFloat {
    val animatedFloat = animatedFloat(if (!visible) 1f else 0f)
    val targetAlpha = if (opaqueChildren) 1f else if (visible) 1f else 0f
    onCommit(targetAlpha) {
        animatedFloat.animateTo(
            targetAlpha,
            anim = animation,
            onEnd = { reason, _ ->
                if (reason == AnimationEndReason.TargetReached) {
                    onAnimationFinish()
                }
            })
    }
    return animatedFloat
}
