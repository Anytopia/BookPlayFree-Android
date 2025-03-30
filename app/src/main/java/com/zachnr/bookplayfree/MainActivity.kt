package com.zachnr.bookplayfree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zachnr.bookplayfree.designsystem.theme.BookPlayFreeTheme
import com.zachnr.bookplayfree.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookPlayFreeTheme {
                AppNavigation()
            }
        }
    }
}
