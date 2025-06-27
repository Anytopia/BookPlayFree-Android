package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model

data class SettingOrderingGroupUI(
    val groupId: String = "",
    val groupTitle: String = "",
    val menus: List<SettingOrderingItemUI> = emptyList()
)
data class SettingOrderingItemUI(
    val itemId: String = "",
    val itemTitle: String = ""
)
