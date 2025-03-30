package com.zachnr.bookplayfree.splashscreen.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zachnr.bookplayfree.designsystem.theme.Grey50
import com.zachnr.bookplayfree.splashscreen.R
import com.zachnr.bookplayfree.splashscreen.presentation.model.SplashScreenEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnimatedSplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = koinViewModel()
) {
    AnimatedSplashScreen(viewModel::sendEvent)
}
@Composable
private fun AnimatedSplashScreen(
    event: (SplashScreenEvent) -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_splashscreen))
    val progress by animateLottieCompositionAsState(composition)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.Center)
                .size(150.dp),
            composition = composition,
            progress = { progress },
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 50.dp)
                .clickable {
                    event(SplashScreenEvent.NavigateToDashboard)
                },
            text = "Book Play Free",
            fontSize = 20.sp,
            color = Grey50,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun AnimatedSplashScreenPreview() {
    AnimatedSplashScreen()
}
