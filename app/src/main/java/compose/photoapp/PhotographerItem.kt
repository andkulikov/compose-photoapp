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

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Composable
fun PhotographerCard(
    photographer: Photographer,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val padding = 16.dp
    Column(
        modifier.clickable(onClick = onClick)
            .padding(
                top = padding / 2,
                start = padding,
                end = padding,
                bottom = padding / 2
            ).fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            FadeInImage(
                id = photographer.avatar,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.size(padding))
            Column {
                Text(
                    photographer.name,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                )
                ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                    Text(photographer.lastSeenOnline, style = MaterialTheme.typography.caption)
                }
            }
        }
        Spacer(Modifier.size(padding))
        val alpha = animatedFloat(0f)
        onActive {
            alpha.animateTo(
                1f, tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
        }
        Card(elevation = 4.dp) {
            FadeInImage(
                photographer.mainImage,
                Modifier.fillMaxWidth().height(250.dp)
            )
        }
    }
}

@Preview()
@Composable
fun PhotographerItemPreview() {
    val demoPhotographer = Photographer(
        "id",
        "Patricia Stevenson",
        "3 minutes ago",
        R.drawable.ava1,
        R.drawable.image1,
        "0",
        "0",
        emptyList(),
        emptyMap()
    )
    MaterialTheme {
        PhotographerCard(demoPhotographer, {})
    }
}
