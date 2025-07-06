package com.zachnr.bookplayfree.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.zachnr.bookplayfree.datastore.model.UserDataPref
import com.zachnr.bookplayfree.datastore.model.UserGoalPref
import com.zachnr.bookplayfree.datastore.model.UserSettingPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal class DataStoreSourceImpl(
    private val dataStore: DataStore<UserDataPrefProto>
) : DataStoreSource {

    override val userPreference: Flow<UserDataPref> = dataStore.data
        .catch { exception ->
            emit(UserDataPrefProto.getDefaultInstance())
            Log.e(TAG, "Error reading user preferences.", exception)
        }
        .map { proto ->
            UserDataPref(
                settingData = UserSettingPref(
                    langTargetTranslation = proto.settingData.langTargetTranslation,
                    isEffect3d = proto.settingData.isEffect3D,
                    isShakeToNext = proto.settingData.isShakeToNext,
                    isReadWhenLaunch = proto.settingData.isReadWhenLaunch
                ),
                isShowDashboardCoachMark = proto.isShowDashboardCoachMark,
                isShowReadingCoachMark = proto.isShowReadingCoachMark,
                userGoal = UserGoalPref(
                    readingTime = proto.userGoal.readingTime,
                    page = proto.userGoal.page,
                    book = proto.userGoal.book
                )
            )
        }

    override suspend fun setLangTranslationTarget(langCode: String) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setSettingData(
                        currentPreferences.settingData.toBuilder()
                            .setLangTargetTranslation(langCode)
                    )
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update language target translation", ioException)
        }
    }

    override suspend fun setEffect3DEnabled(isEnabled: Boolean) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setSettingData(
                        currentPreferences.settingData.toBuilder()
                            .setIsEffect3D(isEnabled)
                    )
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update 3D effect setting", ioException)
        }
    }

    override suspend fun setShakeToNextEnabled(isEnabled: Boolean) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setSettingData(
                        currentPreferences.settingData.toBuilder()
                            .setIsShakeToNext(isEnabled)
                    )
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update shake to next setting", ioException)
        }
    }

    override suspend fun setReadWhenLaunchEnabled(isEnabled: Boolean) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setSettingData(
                        currentPreferences.settingData.toBuilder()
                            .setIsReadWhenLaunch(isEnabled)
                    )
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update read when launch setting", ioException)
        }
    }

    override suspend fun setShowDashboardCoachMark(isShown: Boolean) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setIsShowDashboardCoachMark(isShown)
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update dashboard coach mark visibility", ioException)
        }
    }

    override suspend fun setShowReadingCoachMark(isShown: Boolean) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setIsShowReadingCoachMark(isShown)
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update reading coach mark visibility", ioException)
        }
    }

    override suspend fun setUserGoalReadingTime(minutes: Int) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setUserGoal(
                        currentPreferences.userGoal.toBuilder()
                            .setReadingTime(minutes)
                    )
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update user goal reading time", ioException)
        }
    }

    override suspend fun setUserGoalPages(pages: Int) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setUserGoal(
                        currentPreferences.userGoal.toBuilder()
                            .setPage(pages)
                    )
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update user goal pages", ioException)
        }
    }

    override suspend fun setUserGoalBooks(books: Int) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setUserGoal(
                        currentPreferences.userGoal.toBuilder()
                            .setBook(books)
                    )
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to update user goal book", ioException)
        }
    }

    override suspend fun clearUserPreferences() {
        try {
            dataStore.updateData {
                // This creates a new instance with all fields at their default values
                // as defined in your .proto file or the standard proto3 defaults.
                UserDataPrefProto.getDefaultInstance()
            }
        } catch (ioException: IOException) {
            Log.e(TAG, "Failed to clear user preferences", ioException)
        }
    }

    companion object {
        private const val TAG = "DataStoreSourceImpl"
    }
}
