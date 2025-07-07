package com.zachnr.bookplayfree.data.repository.setting

import com.zachnr.bookplayfree.datastore.DataStoreSource
import com.zachnr.bookplayfree.datastore.model.UserDataPref
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingMenuDomain
import com.zachnr.bookplayfree.domain.repository.setting.SettingRepository
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import com.zachnr.bookplayfree.utils.utils.SettingMenu
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SettingRepositoryImpl(
    private val dataStoreSource: DataStoreSource,
    private val dispatcher: DispatcherProvider
) : SettingRepository {

    override val settingData = dataStoreSource.userPreference.map {
        mapDataPreferenceToSettingMenu(it)
    }

    /**
     * This function is to build the setting menu based on the user preference and map it to domain
     * model.
     */
    private suspend fun mapDataPreferenceToSettingMenu(
        pref: UserDataPref
    ): List<SettingOrderingDomain> = withContext(dispatcher.io) {
        val fileSync = SettingOrderingMenuDomain(menuId = SettingMenu.FILE_SYNC)
        val readBook = SettingOrderingMenuDomain(
            menuId = SettingMenu.READ_BOOK_WHEN_LAUNCH,
            isActive = pref.settingData.isReadWhenLaunch
        )
        val setGoals = SettingOrderingMenuDomain(menuId = SettingMenu.SET_GOALS)
        val effect3d = SettingOrderingMenuDomain(
            menuId = SettingMenu.EFFECT_3D,
            isActive = pref.settingData.isEffect3d
        )
        val shakeToNext = SettingOrderingMenuDomain(
            menuId = SettingMenu.SHAKE_TO_NEXT,
            isActive = pref.settingData.isShakeToNext
        )
        val setting: List<SettingOrderingDomain> = buildList {
            add(
                SettingOrderingDomain(
                    groupId = SettingMenu.GENERAL,
                    menus = listOf(fileSync, readBook, setGoals)
                )
            )
            add(
                SettingOrderingDomain(
                    groupId = SettingMenu.READING,
                    menus = listOf(effect3d, shakeToNext)
                )
            )
        }
        return@withContext setting
    }

    override suspend fun setIsReadBookWhenLaunch(value: Boolean) =
        dataStoreSource.setReadWhenLaunchEnabled(value)

    override suspend fun setIsEffect3d(value: Boolean) =
        dataStoreSource.setEffect3DEnabled(value)

    override suspend fun setIsShakeToNext(value: Boolean) =
        dataStoreSource.setShakeToNextEnabled(value)
}
