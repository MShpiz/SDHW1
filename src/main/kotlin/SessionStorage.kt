import kotlinx.serialization.Serializable

@Serializable
class SessionStorage(override val items: MutableList<Session>) : Storage<Session>()