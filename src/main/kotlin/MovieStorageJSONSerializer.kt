import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MovieStorageJSONSerializer : ISerializer<MovieStorage> {
    override fun serialize(unit: MovieStorage): String {
        return Json.encodeToString(unit)
    }

    override fun deserialize(string: String): MovieStorage? {
        val result: MovieStorage? = try {
            Json.decodeFromString<MovieStorage>(string)
        } catch (e: SerializationException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
        return result
    }

}