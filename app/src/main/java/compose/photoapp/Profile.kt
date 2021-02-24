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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Profile(photographer: Photographer, modifier: Modifier = Modifier) {
    val padding = 16.dp
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.onSurface,
        contentColor = MaterialTheme.colors.surface
    ) {
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
            var selectedGroup by rememberSaveable { mutableStateOf(groups.first()) }
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
    val padding = 8.dp
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = padding * 2, end = padding),
        horizontalArrangement = Arrangement.spacedBy(padding)
    ) {
        items(tags) {
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .border(
                        1.dp,
                        LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                        CircleShape
                    )
                    .padding(padding)
            )
        }
    }
}

@Preview(widthDp = 200)
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
        listOf("travel", "urban", "fashion", "food", "mood", "home"),
        emptyMap()
    )
    PhotoAppTheme {
        Surface {
            TagsList(demoPhotographer.tags)
        }
    }
}
