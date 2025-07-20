package com.zachnr.bookplayfree.dashboard.presentation.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.designsystem.theme.GreenForest
import com.zachnr.bookplayfree.shared.model.UiWrapper
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.goalcard.CardState
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardComponent
import com.zachnr.bookplayfree.uicomponent.goalcard.GoalCardUIModel
import com.zachnr.bookplayfree.uicomponent.goalcard.PhantomTransitionCard
import com.zachnr.bookplayfree.uicomponent.goalcard.SwipeableAnimatedCard
import com.zachnr.bookplayfree.uicomponent.goalcard.SwipeableSpringCard
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    mainShareViewModel: MainActivitySharedVM
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        mainShareViewModel.quote.collect {
            when (it) {
                is UiWrapper.Success -> {
                    viewModel.updateQuote(it.data)
                }

                else -> {}
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        state = state.value
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier,
    state: HomeState
) {
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        SearchBarDashboard()
//        Text(
//            text = "Welcome",
//            fontSize = 24.sp,
//            modifier = Modifier.padding(horizontal = 14.dp)
//        )
//        Text(
//            text = state.quote,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Light,
//            fontStyle = FontStyle.Italic,
//            modifier = Modifier.padding(horizontal = 14.dp)
//        )
//        // TODO: Handle hardcoded text
//        Text(
//            text = "Daily Goals",
//            fontSize = 24.sp,
//            modifier = Modifier.padding(horizontal = 14.dp)
//        )
//        GoalsTrackerProgressComponent(
//            modifier = Modifier.padding(horizontal = 14.dp)
//        )
//    }
    var selectedCard by remember { mutableStateOf<Int?>(null) }

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF1A1A1A))
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        items(5) { index ->
//            PhantomTransitionCard(
//                isSelected = selectedCard == index,
//                onClick = {
//                    selectedCard = if (selectedCard == index) null else index
//                }
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_goals), // Use your phantom icon
//                        contentDescription = "Phantom Champion",
//                        modifier = Modifier.size(48.dp),
//                        tint = Color.White
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        text = "Phantom Champion $index",
//                        color = Color.White,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = "Level ${10 + index}",
//                        color = Color.White.copy(alpha = 0.7f),
//                        fontSize = 14.sp
//                    )
//                }
//            }
//        }
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoalCardComponent(
            state = GoalCardUIModel(
                target = 13,
                targetText = "Pages",
                isActive = true,
                colorTheme = GreenForest
            )
        )
    }
}

@Composable
fun SwipeableCardDemo() {
    val cardStates = remember {
        listOf(
            CardState(
                index = 0,
                title = "Phantom Champion",
                description = "Master of illusions and stealth attacks",
                backgroundColor = Color(0xFF9C27B0),
                icon = Icons.Default.Visibility
            ),
            CardState(
                index = 1,
                title = "Fire Warrior",
                description = "Devastating flame attacks and burning rage",
                backgroundColor = Color(0xFFFF5722),
                icon = Icons.Default.Whatshot
            ),
            CardState(
                index = 2,
                title = "Ice Guardian",
                description = "Frozen shields and crystalline defense",
                backgroundColor = Color(0xFF2196F3),
                icon = Icons.Default.AcUnit
            ),
            CardState(
                index = 3,
                title = "Storm Caller",
                description = "Lightning strikes and thunder roars",
                backgroundColor = Color(0xFF4CAF50),
                icon = Icons.Default.Bolt
            ),
            CardState(
                index = 4,
                title = "Shadow Assassin",
                description = "Silent strikes from the darkness",
                backgroundColor = Color(0xFF424242),
                icon = Icons.Default.Nightlight
            )
        )
    }

    var currentStateIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SwipeableSpringCard(
            cardStates = cardStates,
            initialStateIndex = currentStateIndex,
            onStateChange = { newIndex ->
                currentStateIndex = newIndex
            },
            swipeThreshold = 120f
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Swipe left or right to change champion",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val defaultMsg = "\"A reader lives a thousand lives before he dies. " +
        "The man who never reads lives only one.\" — George R.R. Martin"
    HomeScreen(
        modifier = Modifier,
        state = HomeState(quote = defaultMsg)
    )
}
