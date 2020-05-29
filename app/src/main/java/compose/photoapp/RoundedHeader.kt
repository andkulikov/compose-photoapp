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