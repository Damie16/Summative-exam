package za.ac.imadexam

object SongDataHolder {
    val playlist = mutableListOf<Song>()
}

// Make sure your Song data class is outside MainActivity (or copy it here)
data class Song(
    var title: String,
    var artist: String,
    var rating: Float,
    var comments: String
) {
    override fun toString(): String {
        return "$title - $artist (Rating: $rating/5)"
    }
}