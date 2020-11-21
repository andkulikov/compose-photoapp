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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ProfileHeader(photographer: Photographer) {
    val padding = 16.dp

    Row(
        modifier = Modifier.fillMaxWidth().padding(start = padding, end = padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FadeInImage(
            id = photographer.avatar,
            modifier = Modifier.size(72.dp).clip(CircleShape)
        )
        Spacer(Modifier.size(padding))
        Column(Modifier.weight(1f)) {
            Text(
                text = photographer.name,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FollowerInfo(text = "followers", number = photographer.numOfFollowers)
                FollowerInfo(text = "following", number = photographer.numOfFollowing)
                Button(onClick = {}, shape = CircleShape) {
                    Text("Follow")
                }
            }
        }
    }
}

@Composable
private fun FollowerInfo(text: String, number: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = number, style = MaterialTheme.typography.subtitle2)
        Text(text = text, style = MaterialTheme.typography.caption)
    }
}