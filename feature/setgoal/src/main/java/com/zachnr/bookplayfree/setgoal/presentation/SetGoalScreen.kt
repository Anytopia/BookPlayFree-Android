package com.zachnr.bookplayfree.setgoal.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.setgoal.model.CardType
import com.zachnr.bookplayfree.setgoal.model.GoalItem
import com.zachnr.bookplayfree.setgoal.model.SetGoalState
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
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        // TODO: To be implemented
        state
    }
}

@Preview(showBackground = true)
@Composable
private fun SetGoalScreenPreview() {
    val previewState = GoalItem(
        type = CardType.PAGE,
        isActive = false,
        target = 2
    )
    SetGoalScreen(
        state = SetGoalState.Success(
            goals = listOf(previewState)
        )
    )
}
