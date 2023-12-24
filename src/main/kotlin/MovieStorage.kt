import kotlinx.serialization.Serializable

@Serializable
data class MovieStorage(override val items: MutableList<Movie>) : Storage<Movie>()