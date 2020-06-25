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

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun RoundedHeader(title: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(50, 50, 0, 0)
    ) {
        val padding = 16.dp
        Text(
            text = title,
            modifier = Modifier.padding(start = padding, top = padding, end = padding),
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun RoundedHeaderPreview() {
    MaterialTheme {
        RoundedHeader(title = "Header")
    }
}