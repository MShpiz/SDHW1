import kotlinx.serialization.Serializable

@Serializable
abstract class Storage<T> {
    abstract val items: MutableList<T>
}