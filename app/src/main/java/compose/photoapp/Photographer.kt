package compose.photoapp

data class Photographer(
    val id: String,
    val name: String,
    val lastSeenOnline: String,
    val avatar: Int,
    val mainImage: Int,
    val numOfFollowers: String,
    val numOfFollowing: String,
    val tags: List<String>,
    val photos: Map<String, List<Int>>
)
