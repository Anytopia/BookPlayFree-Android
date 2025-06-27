package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.mapper

import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingGroupUI
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingItemUI
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.uicomponent.utils.ResourcesUtils.fromDynamicResources
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
                        itemTitle = it.fromDynamicResources()
                    )
                }
            )
        }
    }
}
