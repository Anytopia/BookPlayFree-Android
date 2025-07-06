package com.zachnr.bookplayfree.datastore.model

// TODO: Determine whether put this class here or in domain module
data class UserDataPref(
    val settingData: UserSettingPref = UserSettingPref(),
    val isShowDashboardCoachMark: Boolean = false,
    val isShowReadingCoachMark: Boolean = false,
    val userGoal: UserGoalPref = UserGoalPref()
)

data class UserSettingPref(
    val langTargetTranslation: String = "id",
    val isEffect3d: Boolean = true,
    val isShakeToNext: Boolean = false,
    val isReadWhenLaunch: Boolean = false
)

data class UserGoalPref(
    val readingTime: Int = 0,
    val page: Int = 0,
    val book: Int = 0
)
