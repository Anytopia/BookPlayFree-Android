package com.zachnr.bookplayfree.uicomponent.utils

import com.zachnr.bookplayfree.uicomponent.R

/**
 * Beside adding the resource in strings.xml, bound the "key" to the id here.
 *
 * Note: Only use for dynamic text resource
 */
internal val dynamicTextMap = mapOf(
    "setting_general" to R.string.setting_general,
    SettingMenu.FILE_SYNC to R.string.setting_file_sync,
    SettingMenu.READ_BOOK_WHEN_LAUNCH to R.string.setting_read_book_when_launch,
    SettingMenu.SET_GOALS to R.string.setting_set_goals,
    "setting_reading" to R.string.setting_reading,
    SettingMenu.EFFECT_3D to R.string.setting_3d_effect,
    SettingMenu.SHAKE_TO_NEXT to R.string.setting_shake_to_next,
)
