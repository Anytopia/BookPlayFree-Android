package com.zachnr.bookplayfree

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.zachnr.bookplayfree.designsystem.theme.BookPlayFreeTheme
import com.zachnr.bookplayfree.navigation.AppNavigation
import com.zachnr.bookplayfree.utils.RefreshRateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {

    private val remoteConfig: FirebaseRemoteConfig by inject()
    private val refreshRateManager: RefreshRateManager by lazy {
        RefreshRateManager(this)
    }

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

    override fun onUserInteraction() {
        super.onUserInteraction()
        refreshRateManager.onInteractionDetected()
    }

    /**
     * Function to fetch firebase remote config when the app is launched.
     * Note: Move this function to data layer or view model later
     */
    private fun fetchFirebaseConfig() = lifecycleScope.launch(Dispatchers.IO) {
        try {
            remoteConfig.fetchAndActivate()
        } catch (e: Exception) {
            Log.e(TAG, "Remote config fetch failed", e)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
