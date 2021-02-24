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

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PhotoAppTheme(content: @Composable() () -> Unit) {
    val primary = Color(0xffd32f2f)
    val lightColors = lightColors(
        primary = primary
    )
    val darkColors = darkColors(
        primary = primary,
        onPrimary = Color.White
    )
    val colors = if (isSystemInDarkTheme()) darkColors else lightColors
    MaterialTheme(colors = colors, content = content)
}

@Preview
@Composable
fun PhotoAppThemePreview() {
    PhotoAppTheme {
        Surface {
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Demo")
            }
        }
    }
}
