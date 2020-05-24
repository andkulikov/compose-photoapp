package compose.photoapp

data class Photographer(
    val id: String,
    val name: String,
    val lastSeenOnline: String,
    val avatar: Int,
    val mainImage: Int
)
