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

import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb

@Composable
fun StatusBarColorProvider(window: Window) {
    val color = MaterialTheme.colors.onSurface
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        DisposableEffect(color) {
            window.statusBarColor = color.toArgb()
            val isLight = color.luminance() > 0.5f
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                if (isLight) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
            onDispose {}
        }
    }
}
