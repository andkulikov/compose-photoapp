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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
            Image(
                painter = painterResource(id = photographer.avatar),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = "avatar"
            )
            Spacer(Modifier.size(padding))
            Column {
                Text(
                    photographer.name,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                )
                Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                    Text(photographer.lastSeenOnline, style = MaterialTheme.typography.caption)
                }
            }
        }
        Spacer(Modifier.size(padding))
        Card(elevation = 4.dp) {
            Image(
                painterResource(id = photographer.mainImage),
                contentDescription = "main image",
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
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
