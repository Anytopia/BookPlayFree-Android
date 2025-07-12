package com.zachnr.bookplayfree.uicomponent.icons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zachnr.bookplayfree.designsystem.icons.BpfIcons
import com.zachnr.bookplayfree.designsystem.theme.BlackOpacity33
import com.zachnr.bookplayfree.uicomponent.icons.CircularIconButtonConst.SCALE_ICON

/**
 * A circular icon button composable with background black opacity 33%.
 *
 * @param modifier The modifier to be applied to the button.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param iconId The resource ID of the icon to be displayed. Defaults to [BpfIcons.chevronWhiteLeft].
 * @param size The size of the button in dp. Defaults to 32.
 * The icon size will be 60% of this value.
 */
@Composable
fun CircularIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @DrawableRes iconId: Int = BpfIcons.chevronWhiteLeft,
    size: Int = 32
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .background(
                color = BlackOpacity33,
                shape = CircleShape
            ).clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size((size * SCALE_ICON).dp),
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CircularIconButtonPreview() {
    CircularIconButton()
}

private object CircularIconButtonConst {
    const val SCALE_ICON = 0.33f
}
