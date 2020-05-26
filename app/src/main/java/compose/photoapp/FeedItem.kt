package compose.photoapp

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ProvideEmphasis
import androidx.ui.material.ripple.ripple
import androidx.ui.res.imageResource
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun FeedItem(photographer: Photographer, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Clickable(onClick = onClick, modifier = modifier.ripple()) {
        Column(Modifier.fillMaxWidth()) {
            val padding = 16.dp
            Row(
                Modifier.fillMaxWidth(),
                verticalGravity = Alignment.CenterVertically
            ) {
                Image(
                    imageResource(id = photographer.avatar),
                    Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.size(padding))
                Column {
                    Text(
                        photographer.name,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                    )
                    ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                        Text(photographer.lastSeenOnline, style = MaterialTheme.typography.caption)
                    }
                }
            }
            Card(
                Modifier.padding(top = padding, bottom = padding).fillMaxWidth(),
                elevation = 4.dp
            ) {
                Image(
                    imageResource(id = photographer.mainImage),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview()
@Composable
fun FeedItemPreview() {
    val demoPhotographer = Photographer(
        "id",
        "Patricia Stevenson",
        "3 minutes ago",
        R.drawable.ava1,
        R.drawable.image1,
        "0",
        "0",
        emptyList(),
        emptyMap()
    )
    MaterialTheme {
        FeedItem(demoPhotographer, {})
    }
}
