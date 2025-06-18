package com.zachnr.bookplayfree.dashboard.presentation.pages.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun LibraryScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This is library screen", modifier = Modifier.align(Alignment.Center))
    }
}
