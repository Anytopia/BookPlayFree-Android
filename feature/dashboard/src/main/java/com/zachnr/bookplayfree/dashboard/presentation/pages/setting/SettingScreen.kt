package com.zachnr.bookplayfree.dashboard.presentation.pages.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingGroupUI
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingItemUI
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingState
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingState
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.utils.SettingConst.MENU_HEIGHT
import com.zachnr.bookplayfree.designsystem.theme.GreenForest
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.searchbar.SearchBarDashboard
import com.zachnr.bookplayfree.uicomponent.utils.SettingMenu
import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val fbEffect by viewModel.firebaseEffect.collectAsStateWithLifecycle()
    // TODO: Remove fb config from setting
    LaunchedEffect(fbEffect) {
        if (fbEffect is FirebaseEffect.OnConfigUpdate) {
            viewModel.getSettingOrdering()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.removeFirebaseListener()
        }
    }
    SettingScreen(
        state = state,
        modifier = modifier
    )
}

@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    state: SettingState
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (sbSetting, lcSetting) = createRefs()
        SearchBarDashboard(
            modifier = Modifier.constrainAs(sbSetting) { },
            placeholder = stringResource(R.string.setting_search),
            onSearch = {
                // TODO: Add search setting action
            },
            searchResultContent = {
                // TODO: Add search setting result view
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(lcSetting) {
                    top.linkTo(sbSetting.bottom, margin = 14.dp)
                }
        ) {
            when (val ordering = state.settingOrdering) {
                is SettingOrderingState.Success -> {
                    ordering.data.forEach { group ->
                        item(key = group.groupId) {
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 14.dp + 10.dp,
                                    vertical = 8.dp
                                ),
                                text = group.groupTitle,
                                fontSize = 14.sp,
                                color = GreenForest
                            )
                        }
                        // Displays the grouped menu
                        items(items = group.menus) { item ->
                            when (item.itemId) {
                                SettingMenu.FILE_SYNC,
                                SettingMenu.SET_GOALS -> {
                                    SettingItemOrderingBasic(
                                        modifier = Modifier.padding(horizontal = 14.dp),
                                        data = item
                                    )
                                }

                                SettingMenu.EFFECT_3D, SettingMenu.SHAKE_TO_NEXT,
                                SettingMenu.READ_BOOK_WHEN_LAUNCH -> {
                                    SettingItemOrderingCheckBox(
                                        modifier = Modifier.padding(horizontal = 14.dp),
                                        data = item
                                    )
                                }
                            }
                        }
                    }
                }

                else -> item { Unit } // Not render anything
            }
        }
    }
}

@Composable
private fun SettingItemOrderingBasic(
    modifier: Modifier = Modifier,
    data: SettingOrderingItemUI,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(MENU_HEIGHT.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingMenuItemCommon(data = data)
    }
}

@Composable
private fun SettingItemOrderingCheckBox(
    modifier: Modifier = Modifier,
    data: SettingOrderingItemUI,
    onChecked: () -> Unit = {}
) {
    // TODO: Bound the checkbox state with the datastore
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onChecked() }
            .height(MENU_HEIGHT.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingMenuItemCommon(data = data)
        // TODO: Change the checkbox color
        Checkbox(
            modifier = Modifier.padding(end = 10.dp),
            checked = true,
            onCheckedChange = null
        )
    }
}

@Composable
private fun SettingMenuItemCommon(
    modifier: Modifier = Modifier,
    data: SettingOrderingItemUI,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 10.dp, end = 8.dp)
                .size(28.dp),
            painter = painterResource(data.itemIconId),
            contentDescription = null,
            tint = GreenForest
        )
        Column {
            Text(
                modifier = Modifier.padding(4.dp, 8.dp),
                text = data.itemTitle,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    val itemMenu = SettingOrderingItemUI(
        itemId = SettingMenu.FILE_SYNC,
        itemTitle = "File sync",
        itemIconId = R.drawable.ic_file_sync
    )
    val groupMenu1 = SettingOrderingGroupUI(
        groupId = "general1",
        groupTitle = "General",
        menus = listOf(itemMenu, itemMenu, itemMenu)
    )
    val groupMenu2 = SettingOrderingGroupUI(
        groupId = "general2",
        groupTitle = "General",
        menus = listOf(itemMenu, itemMenu, itemMenu)
    )
    SettingScreen(
        modifier = Modifier,
        state = SettingState(
            settingOrdering = SettingOrderingState.Success(
                listOf(groupMenu1, groupMenu2)
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingItemOrderingBasicPreview() {
    SettingItemOrderingBasic(
        data = SettingOrderingItemUI(
            itemId = "",
            itemTitle = "File sync",
            itemIconId = R.drawable.ic_file_sync
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingItemOrderingCheckBoxPreview() {
    SettingItemOrderingCheckBox(
        data = SettingOrderingItemUI(
            itemId = "",
            itemTitle = "File sync",
            itemIconId = R.drawable.ic_file_sync
        )
    )
}
