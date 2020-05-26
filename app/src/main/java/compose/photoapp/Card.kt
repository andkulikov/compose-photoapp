package compose.photoapp

import androidx.compose.Composable
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp


@Composable
fun Card(title: String, content: @Composable() () -> Unit) {
    val padding = 16.dp
    androidx.ui.material.Card(
        modifier = Modifier.fillMaxWidth().padding(top = padding),
        shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
    ) {
        // TODO: remove Column when Surface uses Column inside
        Column(Modifier.padding(start = padding, top = padding, end = padding)) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
            content()
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    MaterialTheme {
        Card(title = "Card") {
            Image(
                asset = imageResource(R.drawable.art_1),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}