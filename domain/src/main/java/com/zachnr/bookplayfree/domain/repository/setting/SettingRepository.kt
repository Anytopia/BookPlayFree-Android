package com.zachnr.bookplayfree.domain.repository.setting

import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain

interface SettingRepository {
    /**
     * Handle get ordering setting from remote config or local json
     *
     * @return [DomainWrapper<SettingOrderingDomain>]
     */
    suspend fun getSettingMenuOrdering(): DomainWrapper<SettingOrderingDomain>
}
