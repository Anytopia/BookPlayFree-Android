package com.zachnr.bookplayfree.uicomponent.spannable

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

/**
 * Applies a custom color style to a specific substring (`targetText`) within the original string,
 * and returns it as an [AnnotatedString] for use in Compose text rendering.
 *
 * Useful when you want to highlight or colorize part of a sentence in Jetpack Compose's [Text]
 * composable.
 *
 * @param targetText The substring to apply the color to. Only the first occurrence will be styled.
 * @param targetColor The [Color] to apply to the [targetText].
 * @param ignoreCase `true` to ignore case when searching for `targetText`. Defaults to `true`.
 * @return An [AnnotatedString] with the [targetText] styled using the specified [targetColor].
 *
 * @throws IllegalArgumentException if [targetText] is not found in the original string.
 *
 * @sample
 * val text = "How many pages will you read daily?".spanSingleText("pages", Color.Red)
 * Text(text = text)
 */
fun String.spanSingleText(
    targetText: String,
    targetColor: Color,
    ignoreCase: Boolean = true
): AnnotatedString {

    val startIndex = this.indexOf(targetText, ignoreCase = ignoreCase)
    check(startIndex != -1) {
        "Target text \"$targetText\" not found in \"$this\""
    }
    val endIndex = startIndex + targetText.length
    return buildAnnotatedString {
        append(this@spanSingleText)
        addStyle(
            style = SpanStyle(
                color = targetColor,
                fontWeight = FontWeight.Normal
            ),
            start = startIndex,
            end = endIndex,
        )
    }
}
