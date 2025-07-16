package com.zachnr.bookplayfree.setgoal.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.designsystem.icons.BpfIcons
import com.zachnr.bookplayfree.designsystem.theme.GrayCharcoal
import com.zachnr.bookplayfree.designsystem.theme.GreenForest
import com.zachnr.bookplayfree.setgoal.model.SetGoalScreenEvent
import com.zachnr.bookplayfree.setgoal.model.SetGoalState
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.helpbubble.BubbleTextTailRight
import com.zachnr.bookplayfree.uicomponent.icons.CircularIconButton
import com.zachnr.bookplayfree.uicomponent.spannable.spanSingleText
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SetGoalScreen(
    modifier: Modifier = Modifier,
    viewModel: SetGoalViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    SetGoalScreen(
        modifier = modifier,
        state = state.value
    )
}

@Composable
internal fun SetGoalScreen(
    modifier: Modifier = Modifier,
    state: SetGoalState,
    navigationEvent: (SetGoalScreenEvent) -> Unit = {}
) {
    when (state) {
        is SetGoalState.Success -> {
            val (colorBackground, colorText, colorSpan) = when {
                state.isActive -> Triple(state.colorTheme, Color.White, GrayCharcoal)
                else -> Triple(Color.White, Color.Gray, state.colorTheme)
            }
            ConstraintLayout(
                modifier = modifier
                    .background(colorBackground)
                    .fillMaxSize()
            ) {
                val (imgSetGoalBack, bubbleSetGoalBubble, imgSetGoalQuestion) = createRefs()
                val (txtTitle) = createRefs()
                CircularIconButton(
                    modifier = Modifier.constrainAs(imgSetGoalBack) {
                        top.linkTo(parent.top, margin = 42.dp)
                        start.linkTo(parent.start, margin = 14.dp)
                    },
                    iconId = BpfIcons.chevronWhiteLeft,
                    onClick = { navigationEvent(SetGoalScreenEvent.OnBackClicked) }
                )
                BubbleTextTailRight(
                    modifier = Modifier.constrainAs(bubbleSetGoalBubble) {
                        top.linkTo(imgSetGoalQuestion.top)
                        bottom.linkTo(imgSetGoalQuestion.bottom)
                        end.linkTo(imgSetGoalQuestion.start, margin = 8.dp)
                    },
                    text = stringResource(R.string.set_goal_need_help)
                )
                CircularIconButton(
                    modifier = Modifier.constrainAs(imgSetGoalQuestion) {
                        top.linkTo(parent.top, margin = 42.dp)
                        end.linkTo(parent.end, margin = 14.dp)
                    },
                    iconId = BpfIcons.helpWhite
                )
                Text(
                    text = state.title.spanSingleText(
                        targetText = state.targetText.lowercase(),
                        targetColor = colorSpan
                    ),
                    modifier = Modifier.constrainAs(txtTitle) {
                        top.linkTo(imgSetGoalBack.bottom, margin = 50.dp)
                        end.linkTo(imgSetGoalQuestion.end, margin = 30.dp)
                        start.linkTo(imgSetGoalBack.start)
                        width = Dimension.fillToConstraints
                    },
                    fontSize = 32.sp,
                    color = colorText,
                    fontWeight = FontWeight.Light,
                    style = TextStyle(
                        lineHeight = 42.sp
                    )
                )
            }
        }

        else -> Unit
    }
}

@Preview(showBackground = true)
@Composable
private fun SetGoalScreenPreview() {
    SetGoalScreen(
        state = SetGoalState.Success(
            isActive = true,
            target = 2,
            title = "How many pages will you read daily?",
            targetText = "Pages",
            colorTheme = GreenForest,
        )
    )
}
