package com.zachnr.bookplayfree.uicomponent.goalcard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

data class CardState1(
    val color: Color,
    val text: String,
    val description: String
)

@Composable
fun SwipeableAnimatedCard(
    modifier: Modifier = Modifier
) {
    var currentStateIndex by remember { mutableStateOf(0) }
    var offsetX by remember { mutableStateOf(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    val CardState1s = listOf(
        CardState1(
            color = Color(0xFF6366F1),
            text = "State 1",
            description = "Swipe left or right to change states!"
        ),
        CardState1(
            color = Color(0xFFEC4899),
            text = "State 2",
            description = "Beautiful spring animations in action"
        ),
        CardState1(
            color = Color(0xFF10B981),
            text = "State 3",
            description = "Smooth transitions with Material Design"
        ),
        CardState1(
            color = Color(0xFFF59E0B),
            text = "State 4",
            description = "Keep swiping to cycle through states"
        )
    )

    val currentState = CardState1s[currentStateIndex]

    // Animated values with spring physics
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isAnimating) 0f else offsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        finishedListener = {
            isAnimating = false
            offsetX = 0f
        }
    )

    val animatedColor by animateColorAsState(
        targetValue = currentState.color,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val scale by animateFloatAsState(
        targetValue = if (abs(offsetX) > 50) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .scale(scale)
            .graphicsLayer {
                translationX = animatedOffsetX
//                rotationZ = animatedOffsetX * 0.02f // Subtle rotation
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        val threshold = 100f
                        when {
                            offsetX > threshold -> {
                                // Swipe right - next state
                                currentStateIndex = (currentStateIndex + 1) % CardState1s.size
                                isAnimating = true
                            }
                            offsetX < -threshold -> {
                                // Swipe left - previous state
                                currentStateIndex = if (currentStateIndex > 0) {
                                    currentStateIndex - 1
                                } else {
                                    CardState1s.size - 1
                                }
                                isAnimating = true
                            }
                            else -> {
                                // Return to center if threshold not met
                                isAnimating = true
                            }
                        }
                    }
                ) { _, dragAmount ->
                    if (!isAnimating) {
                        offsetX += dragAmount.x
                        // Limit the drag distance
                        offsetX = offsetX.coerceIn(-300f, 300f)
                    }
                }
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp + (abs(offsetX) * 0.02f).dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(animatedColor),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentState.text,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentState.description,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Progress indicator
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(CardState1s.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (index == currentStateIndex) {
                                        Color.White
                                    } else {
                                        Color.White.copy(alpha = 0.4f)
                                    },
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }

            // Swipe hint overlay
            if (abs(offsetX) > 50) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(alpha = 0.1f * (abs(offsetX) / 100f))
                        ),
                    contentAlignment = if (offsetX > 0) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Text(
                        text = if (offsetX > 0) "→" else "←",
                        fontSize = 32.sp,
                        color = Color.White,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableCardPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        SwipeableAnimatedCard()
    }

}