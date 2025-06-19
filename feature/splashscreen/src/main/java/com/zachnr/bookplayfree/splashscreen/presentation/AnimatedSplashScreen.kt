package com.zachnr.bookplayfree.splashscreen.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zachnr.bookplayfree.designsystem.theme.Grey50
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import com.zachnr.bookplayfree.splashscreen.R
import com.zachnr.bookplayfree.splashscreen.presentation.model.SplashScreenEvent
import com.zachnr.bookplayfree.splashscreen.presentation.model.SplashScreenState
import com.zachnr.bookplayfree.splashscreen.presentation.utils.SplashScreenConst.SPLASH_SCREEN_LOTTIE_DURATION
import org.koin.androidx.compose.koinViewModel
import kotlin.math.min

@Composable
fun AnimatedSplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = koinViewModel(),
    mainSharedVM: MainActivitySharedVM
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        mainSharedVM.quote.collect {
            viewModel.setIsFetchingQuoteFinished(true)
        }
    }

    AnimatedSplashScreen(
        modifier = modifier,
        state.value,
        viewModel::sendEvent,
        viewModel::setIsLottieAnimationFinished
    )
}

@Composable
private fun AnimatedSplashScreen(
    modifier: Modifier = Modifier,
    state: SplashScreenState,
    event: (SplashScreenEvent) -> Unit = {},
    setLottieFinished: (Boolean) -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_splashscreen))
    val progress by animateLottieCompositionAsState(composition)
    val limitedProgress = remember(progress) { min(progress, SPLASH_SCREEN_LOTTIE_DURATION) }
    LaunchedEffect(limitedProgress, state) {
        if (limitedProgress == SPLASH_SCREEN_LOTTIE_DURATION) { setLottieFinished(true) }
        if (state.isFetchingQuoteFinished && state.isLottieAnimationFinished) {
            event(SplashScreenEvent.NavigateToDashboard)
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.Center)
                .size(150.dp),
            composition = composition,
            progress = { limitedProgress }
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 50.dp),
            text = "Book Play Free",
            fontSize = 20.sp,
            color = Grey50,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedSplashScreenPreview() {
    AnimatedSplashScreen(state = SplashScreenState())
}
