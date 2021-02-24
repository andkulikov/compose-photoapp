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

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PhotographersViewModel : ViewModel() {

    private val _photographers = MutableStateFlow<List<Photographer>>(emptyList())
    val photographers: StateFlow<List<Photographer>> = _photographers

    fun getById(id: String) = photographers.value.first { it.id == id }

    init {
        _photographers.value = mutableListOf(
            Photographer(
                "id1",
                "Patricia Stevenson",
                "3 minutes ago",
                R.drawable.ava1,
                R.drawable.image1,
                "135K",
                "2.6K",
                listOf("food", "urban", "city", "people", "style", "fashion", "environment"),
                mapOf(
                    "City" to listOf(
                        R.drawable.city_1,
                        R.drawable.city_2,
                        R.drawable.city_3,
                        R.drawable.city_4,
                        R.drawable.city_5,
                        R.drawable.city_6
                    ),
                    "Street Art" to listOf(
                        R.drawable.art_1,
                        R.drawable.art_2,
                        R.drawable.art_3,
                        R.drawable.art_4,
                        R.drawable.art_5,
                        R.drawable.art_6
                    )
                )
            ),
            Photographer(
                "id2",
                "Diana Glow",
                "10 minutes ago",
                R.drawable.ava2,
                R.drawable.image2,
                "18K",
                "945",
                listOf("family", "people", "portrait", "nature", "style", "fashion", "environment"),
                mapOf(
                    "Fashion" to listOf(
                        R.drawable.fashion_1,
                        R.drawable.fashion_2,
                        R.drawable.fashion_3,
                        R.drawable.fashion_4,
                        R.drawable.fashion_5,
                        R.drawable.fashion_6
                    ),
                    "Nature" to listOf(
                        R.drawable.nature_1,
                        R.drawable.nature_2,
                        R.drawable.nature_3,
                        R.drawable.nature_4,
                        R.drawable.nature_5,
                        R.drawable.nature_6
                    ),
                    "People" to listOf(
                        R.drawable.people_1,
                        R.drawable.people_2,
                        R.drawable.people_3,
                        R.drawable.people_4,
                        R.drawable.people_5,
                        R.drawable.people_6
                    )
                )
            ),
            Photographer(
                "id3",
                "Kurt Cobain",
                "26 years ago",
                R.drawable.ava3,
                R.drawable.image3,
                "1.9M",
                "42",
                listOf("music", "live", "concert", "rock", "metal"),
                mapOf(
                    "Live" to listOf(
                        R.drawable.live_1,
                        R.drawable.live_2,
                        R.drawable.live_3,
                        R.drawable.live_4,
                        R.drawable.live_5,
                        R.drawable.live_6
                    ),
                    "B/W" to listOf(
                        R.drawable.bw_1,
                        R.drawable.bw_2,
                        R.drawable.bw_3,
                        R.drawable.bw_4,
                        R.drawable.bw_5,
                        R.drawable.bw_6
                    )
                )
            )
        )
    }
}
