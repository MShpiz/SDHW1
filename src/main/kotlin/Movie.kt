import kotlinx.serialization.Serializable

@Serializable
class Movie(val id: String, var name: String, var duration: Long)