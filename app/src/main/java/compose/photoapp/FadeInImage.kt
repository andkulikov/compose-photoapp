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
    val alpha = animatedFloat(0f)
    val image = loadImageResource(id = id).resource.resource
    onCommit(image) {
        alpha.snapTo(0f)
        if (image != null) {
            alpha.animateTo(1f, TweenBuilder<Float>().apply {
                duration = 300
                easing = LinearOutSlowInEasing
            })
        }
    }
    if (image == null) {
        Spacer(modifier = modifier)
    } else {
        Image(
            image,
            modifier.drawOpacity(alpha.value),
            contentScale = ContentScale.Crop
        )
    }
}