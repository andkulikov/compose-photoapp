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

// workaround for the bug in compose until the fix will be released
@Composable
fun <T> CrossfadeTmp(
    current: T?,
    children: @Composable() (T?) -> Unit
) {
    Crossfade(current = current ?: "null") {
        @Suppress("UNCHECKED_CAST")
        children(if (it == "null") null else it as T)
    }
}

// Copy of the Crossfade component which only animates the new item on top of the previous one
// instead of animating both of them. it looks better when the items are fully opaque. This will
// be a second optional mode of Crossfade in the future versions of Compose
@Composable
private fun <T> Crossfade(
    current: T,
    animation: AnimationBuilder<Float> = TweenBuilder(),
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
    var current: T? = null
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
    onAnimationFinish: () -> Unit = {}
): AnimatedFloat {
    val animatedFloat = animatedFloat(if (!visible) 1f else 0f)
    onCommit(visible) {
        if (visible) {
            animatedFloat.animateTo(
                1f,
                anim = animation,
                onEnd = { reason, _ ->
                    if (reason == AnimationEndReason.TargetReached) {
                        onAnimationFinish()
                    }
                })
        }
    }
    return animatedFloat
}
