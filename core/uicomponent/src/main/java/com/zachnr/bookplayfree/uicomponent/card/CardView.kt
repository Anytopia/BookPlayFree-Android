package com.zachnr.bookplayfree.uicomponent.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardView(
    modifier: Modifier = Modifier,
    cornerSize: Int = 14,
    elevation: Int = 0,
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Black
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerSize.dp)
            )
            .fillMaxSize()

    ) {
        content()
    }
}

@Preview
@Composable
fun CardViewPreview() {
    CardView()
}