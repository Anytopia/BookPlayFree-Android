package com.zachnr.bookplayfree.domain.model.setting

import kotlinx.serialization.Serializable

@Serializable
data class SettingOrderingDomain(
    val groupId: String = "",
    val menus: List<String> = emptyList()
)
