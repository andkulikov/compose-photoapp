package compose.photoapp

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text

@Composable
fun Profile(photographer: Photographer, modifier: Modifier = Modifier) {
    Text("TODO profile for ${photographer.name}", modifier)
}