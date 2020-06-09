package compose.photoapp

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.unit.dp

@Composable
fun ProfileHeader(photographer: Photographer) {
    val padding = 16.dp

    Row(
        modifier = Modifier.fillMaxWidth().padding(start = padding, end = padding),
        verticalGravity = Alignment.CenterVertically
    ) {
        FadeInImage(
            id = photographer.avatar,
            modifier = Modifier.size(72.dp).clip(CircleShape)
        )
        Spacer(Modifier.size(padding))
        Column {
            Text(
                text = photographer.name,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalGravity = Alignment.CenterVertically
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
    Column(horizontalGravity = Alignment.CenterHorizontally) {
        Text(text = number, style = MaterialTheme.typography.subtitle2)
        Text(text = text, style = MaterialTheme.typography.caption)
    }
}