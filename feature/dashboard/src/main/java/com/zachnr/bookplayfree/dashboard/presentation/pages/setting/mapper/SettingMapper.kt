package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.mapper

import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingGroupUI
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingItemUI
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.uicomponent.utils.ResourcesUtils.fromDynamicResources
import com.zachnr.bookplayfree.uicomponent.utils.SettingMenu
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import kotlinx.coroutines.withContext

class SettingMapper(private val dispatcher: DispatcherProvider) {
    suspend fun mapSettingOrderingDomainToUI(
        domain: List<SettingOrderingDomain>
    ): List<SettingOrderingGroupUI> = withContext(dispatcher.io) {
        return@withContext domain.map { group ->
            val groupTitle = group.groupId.fromDynamicResources()
            SettingOrderingGroupUI(
                groupId = group.groupId,
                groupTitle = groupTitle,
                menus = group.menus.map {
                    SettingOrderingItemUI(
                        itemId = it,
                        itemTitle = it.fromDynamicResources(),
                        itemIconId = it.getIconDrawableId()
                    )
                }
            )
        }
    }

    private fun String.getIconDrawableId(): Int {
        return when (this) {
            SettingMenu.FILE_SYNC -> R.drawable.ic_file_sync
            SettingMenu.SET_GOALS -> R.drawable.ic_goals
            SettingMenu.EFFECT_3D -> R.drawable.ic_3d_effect
            SettingMenu.SHAKE_TO_NEXT -> R.drawable.ic_phone_shake
            SettingMenu.READ_BOOK_WHEN_LAUNCH -> R.drawable.ic_open_launch
            else -> R.drawable.ic_file_sync
        }
    }
}
