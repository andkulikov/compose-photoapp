package compose.photoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class PhotographersViewModel : ViewModel() {

    private val _photographers = MutableStateFlow<List<Photographer>>(emptyList())
    val photographers: StateFlow<List<Photographer>> = _photographers

    fun getById(id: String?) = photographers.value.firstOrNull { it.id == id }

    init {
        _photographers.value = mutableListOf(
            Photographer(
                "id1",
                "Patricia Stevenson",
                "3 minutes ago",
                R.drawable.ava1,
                R.drawable.image1
            ),
            Photographer(
                "id2",
                "Diana Glow",
                "10 minutes ago",
                R.drawable.ava2,
                R.drawable.image2
            ),
            Photographer(
                "id3",
                "Kurt Cobain",
                "26 years ago",
                R.drawable.ava3,
                R.drawable.image3
            )
        )
    }
}
