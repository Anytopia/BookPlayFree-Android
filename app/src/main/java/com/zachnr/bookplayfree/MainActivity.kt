package com.zachnr.bookplayfree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.zachnr.bookplayfree.designsystem.theme.BookPlayFreeTheme
import com.zachnr.bookplayfree.navigation.AppNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val remoteConfig: FirebaseRemoteConfig by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchFirebaseConfig()
        enableEdgeToEdge()
        setContent {
            BookPlayFreeTheme {
                AppNavigation()
            }
        }
    }

    /**
     * Function to fetch firebase remote config when the app is launched.
     * Note: Move this function to data layer or view model later
     */
    private fun fetchFirebaseConfig() = lifecycleScope.launch(Dispatchers.IO) {
        remoteConfig.fetchAndActivate()
    }
}
