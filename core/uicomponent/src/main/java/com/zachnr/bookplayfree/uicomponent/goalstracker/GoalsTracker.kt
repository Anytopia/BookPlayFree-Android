package com.zachnr.bookplayfree.uicomponent.goalstracker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
import com.zachnr.bookplayfree.uicomponent.goalstracker.GoalTrackerConst.CIRCLE_GAP_PERCENTAGE
import com.zachnr.bookplayfree.uicomponent.goalstracker.GoalTrackerConst.CIRCULAR_TRACK_TRANSPARENCY
import com.zachnr.bookplayfree.uicomponent.goalstracker.GoalTrackerConst.MAX_CIRCLE_SIZE
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
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var sizePx by remember { mutableStateOf(IntSize.Zero) }
        val density = LocalDensity.current
        val widthDp = with(density) { sizePx.width.toDp().coerceIn(0.dp, MAX_CIRCLE_SIZE.dp) }

        Box(
            modifier = Modifier
                .weight(1f)
                .onSizeChanged {
                    sizePx = it
                },
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                data.forEachIndexed { index, item ->
                    GoalTrackerCircularProgress(
                        modifier = Modifier.size(widthDp * (1 - (index * CIRCLE_GAP_PERCENTAGE))),
                        progress = item.currentProgress / item.targetProgress,
                        isEnabled = item.isEnabled,
                        color = item.type.getFillColor(),
                        trackColor = item.type.getFillColor()
                            .copy(alpha = CIRCULAR_TRACK_TRANSPARENCY),
                        leadingIcon = item.type.getLeadingIcon()
                    )
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f),
        ) {
            items(data.size) { index ->
                GoalTrackerTextProgress(
                    modifier = Modifier.fillMaxWidth(),
                    item = data[index]
                )
            }
        }
    }
}

@Composable
fun GoalTrackerCircularProgress(
    modifier: Modifier = Modifier,
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
                .constrainAs(progressCircle) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
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

// TODO: Handle text size for small device
@Composable
fun GoalTrackerTextProgress(
    modifier: Modifier = Modifier,
    item: GoalTrackerUI
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (text, ongoingTarget, target, leadingIcon) = createRefs()
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        val isGoalReached: Boolean = remember { item.targetProgress == item.currentProgress }
        val context = LocalContext.current
        val processedTargetText = item.type.getTitle(context)
            .pluralize(item.targetProgress.toInt())
            .lowercase()
        val targetText = "/${item.targetProgress} $processedTargetText"
        Box(
            modifier = Modifier
                .size(24.dp)
                .constrainAs(leadingIcon) {}
        ) {
            if (isGoalReached) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .scale(0.65f)
                )
            } else {
                DotLeadingCircle(
                    fillColor = item.type.getFillColor(),
                    modifier = Modifier.size(24.dp),
                    size = 10
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
fun DotLeadingCircle(
    modifier: Modifier = Modifier,
    fillColor: Color,
    size: Int = 8
) {
    Canvas(modifier = modifier.size(size.dp)) {
        drawCircle(
            color = fillColor,
            radius = size.toFloat(),
            center = Offset(this.size.width * 0.5f, this.size.height * 0.5f)
        )
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
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
@Preview(showBackground = true)
fun DualDotPreview() {
    DotLeadingCircle(fillColor = RedCoral)
}
