package com.zachnr.bookplayfree.utils.ext

import java.util.Locale

fun String.pluralize(count: Int, locale: Locale = Locale.getDefault()): String {
    if (locale.language != "en") return this // No change for non-English

    if (count == 1) return this

    return when {
        endsWith("y", ignoreCase = true) && length > 1 && !isVowel(this[length - 2]) ->
            dropLast(1) + "ies"
        endsWith("s", ignoreCase = true) ||
                endsWith("x", ignoreCase = true) ||
                endsWith("z", ignoreCase = true) ||
                endsWith("ch", ignoreCase = true) ||
                endsWith("sh", ignoreCase = true) ->
            this + "es"
        else -> this + "s"
    }
}

private fun isVowel(c: Char): Boolean {
    return c.lowercaseChar() in listOf('a', 'e', 'i', 'o', 'u')
}
