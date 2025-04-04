package com.zachnr.bookplayfree.splashscreen.presentation.model

import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class SplashScreenState(
    val isFetchingQuoteFinished: Boolean = false,
    val isLottieAnimationFinished: Boolean = false
) : ViewState
