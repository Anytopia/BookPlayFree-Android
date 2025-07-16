package com.zachnr.bookplayfree.uicomponent.spannable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
 * @return An [AnnotatedString] with the [targetText] styled using the specified [targetColor].
 *
 * @throws IllegalArgumentException if [targetText] is not found in the original string.
 *
 * @sample
 * val text = "How many pages will you read daily?".spanText("pages", Color.Red)
 * Text(text = text)
 */
@Composable
fun String.spanSingleText(
    targetText: String,
    targetColor: Color
): AnnotatedString {

    val startIndex = this@spanSingleText.indexOf(targetText)
    val endIndex = startIndex + targetText.length
    val spannedText: AnnotatedString = buildAnnotatedString {
        append(this@spanSingleText)
        addStyle(
            style = SpanStyle(
                color = targetColor,
                fontWeight = FontWeight.Normal
            ),
            start = this@spanSingleText.indexOf(targetText),
            end = endIndex,
        )
    }
    return spannedText
}
