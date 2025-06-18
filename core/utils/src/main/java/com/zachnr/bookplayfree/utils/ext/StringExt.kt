package com.zachnr.bookplayfree.utils.ext

import java.util.Locale

/**
 * Returns the plural form of the string based on the provided [count] and [locale].
 *
 * This function implements basic English pluralization rules and falls back to returning
 * the original string for other locales or when the [count] is 1.
 *
 * ### English pluralization rules handled:
 * - If the word ends with a consonant followed by 'y', it becomes `-ies` (e.g., "city" → "cities").
 * - If the word ends with `s`, `x`, `z`, `ch`, or `sh`, it adds `-es` (e.g., "box" → "boxes").
 * - Otherwise, it simply adds `-s` (e.g., "car" → "cars").
 *
 * For non-English locales or if [count] is 1, the original string is returned unchanged.
 *
 * @param count The number of items to determine whether to pluralize.
 * @param locale The [Locale] used to determine pluralization rules; defaults to the system locale.
 * @return The pluralized form of the string if applicable, otherwise the original string.
 */
fun String.pluralize(count: Int, locale: Locale = Locale.getDefault()): String {
    return when {
        locale.language != "en" || count == 1 -> this

        endsWith("y", ignoreCase = true) && length > 1 && !isVowel(this[length - 2]) -> {
            dropLast(1) + "ies"
        }

        endsWith("s", ignoreCase = true) ||
        endsWith("x", ignoreCase = true) ||
        endsWith("z", ignoreCase = true) ||
        endsWith("ch", ignoreCase = true) ||
        endsWith("sh", ignoreCase = true) -> this + "es"

        else -> this + "s"
    }
}

private fun isVowel(c: Char): Boolean {
    return c.lowercaseChar() in listOf('a', 'e', 'i', 'o', 'u')
}
