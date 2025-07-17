package com.zachnr.bookplayfree.utils

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A helper class to dynamically manage the display refresh rate
 * based on user interaction. Better used for single activity application
 *
 * When the user interacts with the screen, the refresh rate is set to high (e.g., 120Hz).
 * After a period of inactivity, it drops to a lower refresh rate (e.g., 60Hz) to save battery.
 *
 * Requires Android API 23+ for `Display.Mode`, and works better on Android 11+.
 *
 * @param activity The activity whose window refresh rate should be controlled.
 */
class RefreshRateManager(
    private val activity: ComponentActivity
) {
    private var userInteractionTimeOut: Job? = null

    init {
        setTimeOutJob()
    }

    /**
     * Call this method whenever user interaction is detected (e.g., touch, scroll).
     * It cancels any existing timeout job and resets the timer to maintain a high refresh rate.
     */
    fun onInteractionDetected() {
        setTimeOutJob()
    }

    /**
     * Cancels the current timeout job (if any) and launches a new coroutine that:
     * - Immediately lowers the refresh rate.
     * - Waits for [USER_INTERACTION_TIMEOUT] duration.
     * - Then increases the refresh rate again.
     */
    private fun setTimeOutJob() {
        userInteractionTimeOut?.cancel()
        userInteractionTimeOut = activity.lifecycleScope.launch {
            setupRefreshRate(false)
            delay(USER_INTERACTION_TIMEOUT)
            setupRefreshRate(true)
        }
    }

    /**
     * Applies the highest or lowest available refresh rate from the supported display modes.
     *
     * @param isLow Whether to set the refresh rate to the lowest (true) or highest (false).
     */
    private fun setupRefreshRate(isLow: Boolean = true) {
        runCatching {
            val display = activity.display
            if (display != null) {
                val supportedModes = display.supportedModes
                val highestMode = if (isLow) {
                    supportedModes.minBy { it.refreshRate }
                } else {
                    supportedModes.maxBy { it.refreshRate }
                }
                val layoutParams = activity.window.attributes
                layoutParams.preferredDisplayModeId = highestMode.modeId
                activity.window.attributes = layoutParams
            }
        }
    }

    companion object {
        private const val USER_INTERACTION_TIMEOUT = 5000L
    }
}