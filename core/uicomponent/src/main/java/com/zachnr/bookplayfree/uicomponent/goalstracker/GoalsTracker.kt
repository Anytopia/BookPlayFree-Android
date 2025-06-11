package com.zachnr.bookplayfree.uicomponent.goalstracker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zachnr.bookplayfree.designsystem.theme.Grey
import com.zachnr.bookplayfree.designsystem.theme.Grey15
import com.zachnr.bookplayfree.designsystem.theme.Grey50
import com.zachnr.bookplayfree.designsystem.theme.RedCoral
import com.zachnr.bookplayfree.designsystem.theme.RedCoral15
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.goalstracker.GoalTrackerConst.CIRCULAR_TRACK_TRANSPARENCY
import com.zachnr.bookplayfree.uicomponent.goalstracker.GoalTrackerConst.INITIAL_CIRCULAR_RADIUS
import com.zachnr.bookplayfree.uicomponent.goalstracker.GoalTrackerConst.RANGE_EACH_CIRCLE
import com.zachnr.bookplayfree.utils.ext.pluralize

private val trackerPlaceholder: List<GoalTrackerUI> = listOf(
    GoalTrackerUI(
        type = ProgressType.PAGE,
        currentProgress = 50f,
        targetProgress = 75f,
        isEnabled = true
    ),
    GoalTrackerUI(
        type = ProgressType.BOOK,
        currentProgress = 50f,
        targetProgress = 75f,
        isEnabled = true
    ),
    GoalTrackerUI(
        type = ProgressType.MINUTES,
        currentProgress = 75f,
        targetProgress = 75f,
        isEnabled = true
    )
)

@Composable
fun GoalsTrackerProgress(
    modifier: Modifier = Modifier,
    data: List<GoalTrackerUI> = trackerPlaceholder
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            data.forEachIndexed { index, item ->
                item.type.getTitle(LocalContext.current)
                GoalTrackerCircularProgress(
                    modifier = modifier,
                    radius = INITIAL_CIRCULAR_RADIUS + (index * RANGE_EACH_CIRCLE),
                    progress = item.currentProgress / item.targetProgress,
                    isEnabled = item.isEnabled,
                    color = item.type.getFillColor(),
                    trackColor = item.type.getFillColor().copy(alpha = CIRCULAR_TRACK_TRANSPARENCY),
                    leadingIcon = item.type.getLeadingIcon()
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data.size) { index ->
                GoalTrackerTextProgress(
                    modifier = modifier,
                    item = data[index]
                )
            }
        }
    }
}

@Composable
fun GoalTrackerCircularProgress(
    modifier: Modifier = Modifier,
    radius: Int,
    progress: Float = 0f,
    isEnabled: Boolean = true,
    color: Color = RedCoral,
    trackColor: Color = RedCoral15,
    leadingIcon: Int
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val colorState = if (isEnabled) color else Grey50
        val trackColorState = if (isEnabled) trackColor else Grey15
        val (progressCircle, icon) = createRefs()
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .size((radius * 2).dp)
                .constrainAs(progressCircle) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            strokeWidth = 12.dp,
            gapSize = 4.dp,
            color = colorState,
            trackColor = trackColorState
        )
        Icon(
            painter = painterResource(id = leadingIcon),
            contentDescription = null,
            modifier = Modifier
                .size(8.dp)
                .constrainAs(icon) {
                    top.linkTo(progressCircle.top, margin = 2.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            tint = Color.White
        )
    }
}

@Composable
fun GoalTrackerTextProgress(
    modifier: Modifier = Modifier,
    item: GoalTrackerUI
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (text, ongoingTarget, target, leadingIcon) = createRefs()
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants. IterateForever
        )
        val isGoalReached: Boolean = remember { item.targetProgress == item.currentProgress }
        val context = LocalContext.current
        val processedTargetText = item.type.getTitle(context)
            .pluralize(item.targetProgress.toInt())
            .lowercase()
        val targetText = "/${item.targetProgress} $processedTargetText"
        Box(
            modifier = Modifier
                .size(32.dp)
                .constrainAs(leadingIcon) {}
        ) {
            if (isGoalReached) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(32.dp)
                        .scale(0.75f)
                )
            } else {
                DualColorCircle(
                    fillColor = item.type.getFillColor(),
                    isGoalReached = isGoalReached,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
        Text(
            text = item.type.getTitle(context),
            modifier = Modifier.constrainAs(text) {
                start.linkTo(leadingIcon.end)
                bottom.linkTo(leadingIcon.bottom)
                top.linkTo(leadingIcon.top)
            },
            color = Grey
        )
        Text(
            text = item.currentProgress.toString(),
            modifier = Modifier.constrainAs(ongoingTarget) {
                start.linkTo(text.start)
                top.linkTo(text.bottom)
            },
            color = item.type.getFillColor(),
            fontSize = 24.sp,
        )
        Text(
            text = targetText,
            modifier = Modifier.constrainAs(target) {
                start.linkTo(ongoingTarget.end)
                bottom.linkTo(ongoingTarget.bottom, margin = 3.dp)
            },
            color = Grey15,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun DualColorCircle(
    modifier: Modifier = Modifier,
    fillColor: Color,
    strokeWidth: Dp = 2.dp,
    isGoalReached: Boolean = true
) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = modifier.size(8.dp)) {
            val diameter = Size(32f, 32f).minDimension
            val radius = diameter / 2f
            val stroke = strokeWidth.toPx()
            val strokeColor: Color = if (isGoalReached) Color.White else fillColor

            drawCircle(
                color = strokeColor,
                radius = radius,
                center = Offset(this.size.width * 0.5f, this.size.height * 0.5f)
            )

            drawCircle(
                color = fillColor,
                radius = radius - stroke,
                center = Offset(this.size.width * 0.5f, this.size.height * 0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoalsTrackerProgressPreview() {
    GoalsTrackerProgress()
}

@Preview(showBackground = true)
@Composable
fun GoalTrackerTextProgressPreview() {
    GoalTrackerTextProgress(
        item = GoalTrackerUI(
            currentProgress = 36f,
            targetProgress = 50f,
        )
    )
}

@Composable
@Preview
fun DualDotPreview() {
    DualColorCircle(fillColor = RedCoral)
}
