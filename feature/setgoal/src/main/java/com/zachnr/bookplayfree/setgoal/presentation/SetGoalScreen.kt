package com.zachnr.bookplayfree.setgoal.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.designsystem.icons.BpfIcons
import com.zachnr.bookplayfree.designsystem.theme.GreenForest
import com.zachnr.bookplayfree.setgoal.model.SetGoalScreenEvent
import com.zachnr.bookplayfree.setgoal.model.SetGoalState
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.helpbubble.BubbleTextTailRight
import com.zachnr.bookplayfree.uicomponent.icons.CircularIconButton
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
            ConstraintLayout(
                modifier = modifier
                    .background(state.colorTheme)
                    .fillMaxSize()
            ) {
                val (imgSetGoalBack, bubbleSetGoalBubble, imgSetGoalQuestion) = createRefs()
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
            isActive = false,
            target = 2,
            title = "How many pages will you read daily?",
            targetText = "Pages",
            colorTheme = GreenForest,
        )
    )
}
