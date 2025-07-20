package com.zachnr.bookplayfree.uicomponent.goalcard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.card.CardView
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardConst.CARD_ACTIVE_ALPHA
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardConst.CARD_BACK_INITIAL_DEGREE
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardConst.CARD_HORIZONTAL_OFFSET_LIMIT
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardConst.CARD_MIDDLE_INITIAL_DEGREE
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardConst.CARD_NON_ACTIVE_ALPHA
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardConst.CARD_THRESHOLD_CHANGE_STATE
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GoalCardComponent(
    modifier: Modifier = Modifier,
    state: GoalCardUIModel,
    onCardSwipedRight: () -> Unit = {},
    onCardSwipedLeft: () -> Unit = {},
) {
    val cardsAlpha = if (state.isActive) CARD_ACTIVE_ALPHA else CARD_NON_ACTIVE_ALPHA
    ConstraintLayout(
        modifier = modifier.alpha(cardsAlpha)
    ) {
        // Constraint layout ids for each component
        val (cardFront, cardMiddle, cardBack) = createRefs()

        // This variable is used to ignore some operation while animation is on going
        var isAnimating by remember { mutableStateOf(false) }
        // Used for moving component when user started to move the component
        var offsetXCardFront by remember { mutableFloatStateOf(0f) }
        // Used when the user released the component -> The animation started
        val animatedOffsetXCardFront by animateFloatAsState(
            targetValue = if (isAnimating) 0f else offsetXCardFront,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            finishedListener = {
                isAnimating = false
                offsetXCardFront = 0f
            }
        )

        var triggerCardDamping by remember { mutableIntStateOf(0) }
        val degreeCardMiddle = remember { Animatable(CARD_MIDDLE_INITIAL_DEGREE) }
        val degreeCardBack = remember { Animatable(CARD_BACK_INITIAL_DEGREE) }
        val shakeCarMiddleAnimSpec = getShakeToLeftKeyFrames(CARD_MIDDLE_INITIAL_DEGREE, 400)
        val shakeCarBackAnimSpec = getShakeToLeftKeyFrames(CARD_BACK_INITIAL_DEGREE, 500)

        LaunchedEffect(triggerCardDamping) {
            // Ignore first shake
            if (triggerCardDamping == 0) return@LaunchedEffect
            launch {
                degreeCardMiddle.animateTo(
                    targetValue = CARD_MIDDLE_INITIAL_DEGREE,
                    animationSpec = shakeCarMiddleAnimSpec
                )
            }
            launch {
                degreeCardBack.animateTo(
                    targetValue = CARD_BACK_INITIAL_DEGREE,
                    animationSpec = shakeCarBackAnimSpec
                )
            }
        }

        CardView(
            modifier = Modifier
                .size(width = 175.dp, height = 225.dp)
                .constrainAs(cardBack) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .graphicsLayer {
                    rotationZ = degreeCardBack.value
                }
            ,
            backgroundColor = Color(0xFFF5F6FF)
        )
        CardView(
            modifier = Modifier
                .size(width = 175.dp, height = 225.dp)
                .constrainAs(cardMiddle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .graphicsLayer {
                    rotationZ = degreeCardMiddle.value
                },
            backgroundColor = Color(0xFFDBD4EC)
        )
        CardFront(
            modifier = Modifier
                .size(width = 175.dp, height = 225.dp)
                .constrainAs(cardFront) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .graphicsLayer {
                    translationX = animatedOffsetXCardFront
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            isAnimating = true
                            triggerCardDamping += 1
                            when {
                                offsetXCardFront > CARD_THRESHOLD_CHANGE_STATE -> {
                                    onCardSwipedRight()
                                }

                                offsetXCardFront < -CARD_THRESHOLD_CHANGE_STATE -> {
                                    onCardSwipedLeft()
                                }
                            }
                        },

                        ) { _, dragAmount ->
                        if (!isAnimating) {
                            offsetXCardFront += dragAmount.x
                            // Limit the drag distance
                            offsetXCardFront = offsetXCardFront.coerceIn(
                                -CARD_HORIZONTAL_OFFSET_LIMIT,
                                CARD_HORIZONTAL_OFFSET_LIMIT
                            )
                        }
                    }
                },
            state = state
        )
    }
}

private fun getShakeToLeftKeyFrames(initialPos: Float, duration: Int): AnimationSpec<Float> {
    val easing = FastOutLinearInEasing
    return keyframes {
        durationMillis = duration
        initialPos + 0f at 0 using easing
        initialPos + -15f at (duration * 0.5).toInt() using easing
        initialPos + 6f at (duration * 0.8).toInt() using easing
        initialPos + 3f at (duration * 0.9).toInt() using easing
        initialPos + 0f at duration using easing
    }
}

@Composable
private fun CardFront(
    modifier: Modifier = Modifier,
    state: GoalCardUIModel
) {
    val (cardColor, targetNumColor, targetTextColor) = if (state.isActive) {
        Triple(Color.White, state.colorTheme, Color.Black)
    } else {
        Triple(state.colorTheme, Color.White, Color.White)
    }
    CardView(
        modifier = modifier,
        backgroundColor = cardColor,
        elevation = 20
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = state.target.toString(),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 70.sp,
                color = targetNumColor,
                textAlign = TextAlign.Center
            )
            Text(
                text = state.targetText,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 32.sp,
                color = targetTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PhantomTransitionCard(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    // Animation values
    val animationProgress by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "phantom_animation"
    )

    // Color spreading effect
    val gradientColors = listOf(
        Color(0xFF9C27B0).copy(alpha = animationProgress * 0.8f), // Purple
        Color(0xFFE91E63).copy(alpha = animationProgress * 0.6f), // Pink
        Color(0xFF673AB7).copy(alpha = animationProgress * 0.9f), // Deep Purple
        Color.Transparent
    )

    // Scale animation for the spreading effect
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "scale_animation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .scale(scale)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 12.dp else 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Base background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF2C2C2C))
            )

            // Animated gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = gradientColors,
                            radius = (300f * animationProgress).coerceIn(1f, 300f),
                            center = Offset(0.5f, 0.5f)
                        )
                    )
            )

            // Animated particles effect
            if (isSelected) {
                ParticleEffect(animationProgress = animationProgress)
            }

            // Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

@Composable
fun ParticleEffect(animationProgress: Float) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val particleCount = 20
        val centerX = size.width / 2
        val centerY = size.height / 2

        repeat(particleCount) { i ->
            val angle = (i * 360f / particleCount) * (Math.PI / 180)
            val distance = animationProgress * 150f

            val x = centerX + cos(angle).toFloat() * distance
            val y = centerY + sin(angle).toFloat() * distance

            drawCircle(
                color = Color.White.copy(alpha = animationProgress * 0.7f),
                radius = 3f * animationProgress,
                center = Offset(x, y)
            )
        }
    }
}

@Composable
fun SwipeableSpringCard(
    modifier: Modifier = Modifier,
    cardStates: List<CardState>,
    initialStateIndex: Int = 0,
    onStateChange: (Int) -> Unit = {},
    swipeThreshold: Float = 100f
) {
    var currentStateIndex by remember { mutableStateOf(initialStateIndex) }
    var offsetX by remember { mutableStateOf(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    // Spring animation for offset
    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
            visibilityThreshold = 1f
        ),
        finishedListener = {
            isAnimating = false
        },
        label = "offset_animation"
    )

    // Scale animation for feedback
    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale_animation"
    )

    // Rotation animation based on swipe
    val rotation by animateFloatAsState(
        targetValue = animatedOffsetX / 20f, // Subtle rotation
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "rotation_animation"
    )

    // Current card state
    val currentState = cardStates[currentStateIndex]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .graphicsLayer {
                translationX = animatedOffsetX
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isAnimating = true
                    },
                    onDragEnd = {
                        val swipeDistance = abs(offsetX)

                        when {
                            offsetX > swipeThreshold -> {
                                // Swipe right - next state
                                currentStateIndex = (currentStateIndex + 1) % cardStates.size
                                onStateChange(currentStateIndex)
                            }

                            offsetX < -swipeThreshold -> {
                                // Swipe left - previous state
                                currentStateIndex = if (currentStateIndex - 1 < 0) {
                                    cardStates.size - 1
                                } else {
                                    currentStateIndex - 1
                                }
                                onStateChange(currentStateIndex)
                            }
                        }

                        // Return to center with spring animation
                        offsetX = 0f
                    }
                ) { _, dragAmount ->
                    if (!isAnimating) {
                        offsetX += dragAmount.x
                        // Limit the drag distance
                        offsetX = offsetX.coerceIn(-200f, 200f)
                    }
                }
            },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Animated background
            AnimatedBackground(
                currentState = currentState,
                swipeProgress = abs(animatedOffsetX) / swipeThreshold
            )

            // Swipe indicators
            SwipeIndicators(
                offsetX = animatedOffsetX,
                threshold = swipeThreshold
            )

            // Card content with state transition
            AnimatedContent(
                targetState = currentState,
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { if (currentStateIndex > 0) it else -it },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { if (currentStateIndex > 0) -it else it },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                },
                label = "content_animation"
            ) { state ->
                CardContent(state = state)
            }
        }
    }
}

@Composable
fun AnimatedBackground(
    currentState: CardState,
    swipeProgress: Float
) {
    val animatedColors by animateColorAsState(
        targetValue = currentState.backgroundColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "background_color"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        animatedColors,
                        animatedColors.copy(alpha = 0.7f),
                        Color.Black.copy(alpha = 0.3f)
                    ),
                    radius = 400f + (swipeProgress * 100f)
                )
            )
    )
}

@Composable
fun SwipeIndicators(
    offsetX: Float,
    threshold: Float
) {
    // Left indicator (Previous)
    AnimatedVisibility(
        visible = offsetX < -threshold * 0.5f,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous",
                modifier = Modifier
                    .padding(32.dp)
                    .size(32.dp),
                tint = Color.White.copy(alpha = abs(offsetX) / threshold)
            )
        }
    }

    // Right indicator (Next)
    AnimatedVisibility(
        visible = offsetX > threshold * 0.5f,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                modifier = Modifier
                    .padding(32.dp)
                    .size(32.dp),
                tint = Color.White.copy(alpha = offsetX / threshold)
            )
        }
    }
}

@Composable
fun CardContent(state: CardState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated icon
        Icon(
            imageVector = state.icon,
            contentDescription = state.title,
            modifier = Modifier.size(64.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Animated title
        Text(
            text = state.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Animated description
        Text(
            text = state.description,
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // State indicator dots
        StateIndicatorDots(
            totalStates = 5, // Assuming 5 states
            currentIndex = state.index
        )
    }
}

@Composable
fun StateIndicatorDots(
    totalStates: Int,
    currentIndex: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalStates) { index ->
            val isSelected = index == currentIndex
            val size by animateDpAsState(
                targetValue = if (isSelected) 12.dp else 8.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "dot_size"
            )

            Box(
                modifier = Modifier
                    .size(size)
                    .background(
                        color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
            )
        }
    }
}

// Data class for card states
data class CardState(
    val index: Int,
    val title: String,
    val description: String,
    val backgroundColor: Color,
    val icon: ImageVector
)

@Preview(showBackground = true, backgroundColor = 0xFFE53E3E)
@Composable
private fun GoalCardComponentPreviewActive() {
//    GoalCardComponent(
//        modifier = Modifier.fillMaxSize(),
//        state = GoalCardUIModel(
//            target = 99,
//            targetText = "pages",
//            isActive = true
//        )
//    )
    var selectedCard by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(5) { index ->
            PhantomTransitionCard(
                isSelected = selectedCard == index,
                onClick = {
                    selectedCard = if (selectedCard == index) null else index
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_goals), // Use your phantom icon
                        contentDescription = "Phantom Champion",
                        modifier = Modifier.size(48.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Phantom Champion $index",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Level ${10 + index}",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun GoalCardComponentPreviewNotActive() {
    GoalCardComponent(
        modifier = Modifier.fillMaxSize(),
        state = GoalCardUIModel(
            target = 99,
            targetText = "pages",
            isActive = false
        )
    )
}

