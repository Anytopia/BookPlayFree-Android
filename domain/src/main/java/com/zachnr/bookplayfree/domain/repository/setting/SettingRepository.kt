package com.zachnr.bookplayfree.domain.repository.setting

import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    /**
     * Retrieves a flow of lists containing setting ordering data.
     * This flow will emit a new list whenever the underlying setting data changes.
     */
    val settingData: Flow<List<SettingOrderingDomain>>

    /**
     * Sets the preference for whether to read the book when the application launches.
     * @param value True to read the book on launch, false otherwise.
     */
    suspend fun setIsReadBookWhenLaunch(value: Boolean)

    /**
     * Sets the preference for enabling or disabling 3D effects.
     * @param value True to enable 3D effects, false to disable.
     */
    suspend fun setIsEffect3d(value: Boolean)

    /**
     * Sets the preference for enabling or disabling the shake-to-next feature.
     * @param value True to enable shake-to-next, false to disable.
     */
    suspend fun setIsShakeToNext(value: Boolean)
}
