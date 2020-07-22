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

import androidx.animation.LinearOutSlowInEasing
import androidx.animation.tween
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
fun FadeInImage(
    id: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val image = loadImageResource(id = id).resource.resource
    if (image == null) {
        Spacer(modifier = modifier)
    } else {
        val alpha = animatedFloat(0f)
        onCommit(image) {
            alpha.snapTo(0f)
            alpha.animateTo(
                1f, tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
        }
        Image(
            image,
            modifier.drawOpacity(alpha.value),
            contentScale = contentScale
        )
    }
}