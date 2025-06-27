package com.zachnr.bookplayfree.utils.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * Parses a JSON [String] into an object of type [T] using a provided [serializer].
 *
 * This version of [parseJson] allows deserialization of generic types by explicitly passing
 * a [KSerializer] for type [T], making it suitable when `reified` type parameters are not available.
 *
 * Example usage:
 * ```
 * @Serializable
 * data class User(val name: String, val age: Int)
 *
 * val json = """{"name":"Alice","age":30}"""
 * val user = parseJson(json, User.serializer())
 * ```
 *
 * @param json The JSON string to parse.
 * @param serializer The Kotlin serialization serializer for type [T].
 * @return An instance of type [T] parsed from the given JSON string.
 * @throws SerializationException If the input JSON is malformed or doesn't match [T]'s structure.
 */
// TODO: Research which is inline reified fun better applied in this function or not
fun <T> parseJson(json: String, serializer: KSerializer<T>): T {
    return Json.decodeFromString(serializer, json)
}
