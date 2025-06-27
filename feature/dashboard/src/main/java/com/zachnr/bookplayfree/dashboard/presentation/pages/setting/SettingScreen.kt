package com.zachnr.bookplayfree.dashboard.presentation.pages.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingState
import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val fbEffect by viewModel.firebaseEffect.collectAsStateWithLifecycle()
    LaunchedEffect(fbEffect) {
        if (fbEffect is FirebaseEffect.OnConfigUpdate) {
            viewModel.getSettingOrdering()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.removeFirebaseListener()
        }
    }
    SettingScreen(
        state = state,
        modifier = modifier
    )
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    state: SettingState
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            is SettingState.Success -> {
                // TODO: To be implemented
            }

            else -> Unit
        }
    }
}
