interface ISerializer<T> {
    fun serialize(unit: T): String

    fun deserialize(string: String): T?

}