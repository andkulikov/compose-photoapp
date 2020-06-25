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

import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.*

@Composable
fun Profile(photographer: Photographer, modifier: Modifier = Modifier) {
    val padding = 16.dp
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.onSurface,
        contentColor = MaterialTheme.colors.surface
    ) {
        // TODO: remove Column when Surface uses it inside
        Column(Modifier.padding(top = 24.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            ProfileHeader(photographer)
            Spacer(modifier = Modifier.weight(1f))
            TagsList(
                photographer.tags,
                Modifier.padding(top = padding, bottom = padding)
            )
            Spacer(modifier = Modifier.weight(1f))
            PortfolioCard(groupedPhotos = photographer.photos)
        }
    }
}

@Composable
private fun PortfolioCard(groupedPhotos: Map<String, List<Int>>) {
    val groups = groupedPhotos.keys.toList()
    RoundedHeader(title = "Portfolio")
    Surface {
        Column {
            var selectedGroup by savedInstanceState { groups.first() }
            PhotosTab(
                groups = groups,
                selectedGroup = selectedGroup,
                onSelected = { selectedGroup = it }
            )
            PhotosGrid(
                groupedPhotos.getValue(selectedGroup),
                Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun TagsList(tags: List<String>, modifier: Modifier = Modifier) {
    HorizontalScroller(modifier = modifier) {
        val padding = 8.dp
        Row(Modifier.padding(start = padding * 2, end = padding)) {
            tags.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .drawBorder(
                            1.dp,
                            EmphasisAmbient.current.disabled.applyEmphasis(contentColor()),
                            CircleShape
                        )
                        .padding(padding)
                )
                Spacer(Modifier.size(padding))
            }
        }
    }
}

@Preview
@Composable
fun TagPreview() {
    val demoPhotographer = Photographer(
        "id",
        "Patricia Stevenson",
        "3 minutes ago",
        R.drawable.ava1,
        R.drawable.image1,
        "101k",
        "894",
        listOf("travel", "urban", "fashion", "food"),
        emptyMap()
    )
    MaterialTheme {
        TagsList(demoPhotographer.tags)
    }
}
