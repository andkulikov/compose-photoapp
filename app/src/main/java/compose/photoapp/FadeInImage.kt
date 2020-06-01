package compose.photoapp

import androidx.animation.LinearOutSlowInEasing
import androidx.animation.TweenBuilder
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.ui.animation.animatedFloat
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.foundation.Image
import androidx.ui.layout.Spacer
import androidx.ui.res.loadImageResource

@Composable
fun FadeInImage(id: Int, modifier: Modifier = Modifier)   {
    val image = loadImageResource(id = id).resource.resource
    if (image == null) {
        Spacer(modifier = modifier)
    } else {
        val alpha = animatedFloat(0f)
        onCommit(image) {
            alpha.snapTo(0f)
            alpha.animateTo(1f, TweenBuilder<Float>().apply {
                duration = 300
                easing = LinearOutSlowInEasing
            })
        }
        Image(
            image,
            modifier.drawOpacity(alpha.value),
            contentScale = ContentScale.Crop
        )
    }
}