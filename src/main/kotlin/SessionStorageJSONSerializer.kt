import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionStorageJSONSerializer: ISerializer<SessionStorage> {
    override fun serialize(unit: SessionStorage): String {
        return Json.encodeToString(unit)
    }

    override fun deserialize(string: String): SessionStorage? {
        val result: SessionStorage? = try {
            Json.decodeFromString<SessionStorage>(string)
        } catch (e: SerializationException){
            // println(e.message)
            null
        } catch (e: IllegalArgumentException) {
            // println(e.message)
            null
        } catch (e: Exception) {
            println(e.message)
            null
        }
        return result
    }
}