package com.zachnr.bookplayfree.uicomponent.helpbubble

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zachnr.bookplayfree.designsystem.theme.BlackOpacity33
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.helpbubble.BubbleTextTailRightConst.TAIL_TRANSPARENCY

/**
 * A composable function that displays a text bubble with a tail on the right side.
 * This is typically used for help or informational messages.
 *
 * @param modifier Optional [Modifier] for this composable.
 * @param text The text string to be displayed inside the bubble.
 * @param onClick A lambda function to be executed when the bubble is clicked.
 *                Defaults to an empty lambda.
 */
@Composable
fun BubbleTextTailRight(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onClick() }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .background(
                    color = BlackOpacity33,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
        Image(
            painter = painterResource(id = R.drawable.bg_tail_black),
            contentDescription = null,
            modifier = Modifier.alpha(TAIL_TRANSPARENCY)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HelpBubblePreview() {
    BubbleTextTailRight(text = "Need help ?")
}

private object BubbleTextTailRightConst {
    const val TAIL_TRANSPARENCY = 0.33f
}

