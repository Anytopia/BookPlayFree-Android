package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.mapper

import android.content.Context
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingGroupUI
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingItemUI
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.uicomponent.R
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import com.zachnr.bookplayfree.utils.utils.SettingMenu
import kotlinx.coroutines.withContext

class SettingMapper(
    private val context: Context,
    private val dispatcher: DispatcherProvider
) {
    suspend fun mapSettingOrderingDomainToUI(
        domain: List<SettingOrderingDomain>
    ): List<SettingOrderingGroupUI> = withContext(dispatcher.io) {
        return@withContext domain.map { group ->
            SettingOrderingGroupUI(
                groupId = group.groupId,
                groupTitle = group.groupId.getGroupTitle(),
                menus = group.menus.map {
                    SettingOrderingItemUI(
                        itemId = it.menuId,
                        itemTitle = it.menuId.getMenuTitle(),
                        itemIconId = it.menuId.getIconDrawableId(),
                        isActive = it.isActive
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

    private fun String.getMenuTitle(): String {
        return when (this) {
            SettingMenu.FILE_SYNC -> context.resources.getString(R.string.setting_file_sync)
            SettingMenu.SET_GOALS -> context.resources.getString(R.string.setting_set_goals)
            SettingMenu.EFFECT_3D -> context.resources.getString(R.string.setting_3d_effect)
            SettingMenu.SHAKE_TO_NEXT -> context.resources.getString(R.string.setting_shake_to_next)
            SettingMenu.READ_BOOK_WHEN_LAUNCH -> {
                context.resources.getString(R.string.setting_read_book_when_launch)
            }

            else -> context.resources.getString(R.string.setting_file_sync)
        }
    }

    private fun String.getGroupTitle(): String {
        return when (this) {
            SettingMenu.GENERAL -> context.resources.getString(R.string.setting_general)
            SettingMenu.READING -> context.resources.getString(R.string.setting_reading)
            else -> context.resources.getString(R.string.setting_reading)
        }
    }
}
