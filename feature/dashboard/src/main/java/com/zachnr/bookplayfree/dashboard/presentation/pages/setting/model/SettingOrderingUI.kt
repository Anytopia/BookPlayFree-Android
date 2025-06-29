package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model

import androidx.annotation.DrawableRes
import com.zachnr.bookplayfree.uicomponent.R

data class SettingOrderingGroupUI(
    val groupId: String = "",
    val groupTitle: String = "",
    val menus: List<SettingOrderingItemUI> = emptyList()
)

data class SettingOrderingItemUI(
    val itemId: String = "",
    val itemTitle: String = "",
    @DrawableRes val itemIconId: Int = R.drawable.ic_file_sync
)
