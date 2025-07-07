package com.zachnr.bookplayfree.dashboard.presentation.pages.setting

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.mapper.SettingMapper
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingOrderingState
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingState
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.domain.repository.setting.SettingRepository
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import com.zachnr.bookplayfree.utils.utils.FlowConst.DEFAULT_STOP_TIMEOUT
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(
    navigator: Navigator,
    private val dispatcher: DispatcherProvider,
    private val settingRepository: SettingRepository,
    private val settingMapper: SettingMapper
) : BaseViewModel<SettingState, ViewEvent, ViewEffect>(navigator) {

    private val settingDomain: StateFlow<List<SettingOrderingDomain>> =
        settingRepository.settingData.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT),
            initialValue = emptyList()
        )

    init {
        observeSettingData()
    }

    override fun setInitialState(): SettingState = SettingState()

    private fun observeSettingData() {
        viewModelScope.launch(dispatcher.io) {
            settingDomain
                .map { domainList ->
                    settingMapper.mapSettingOrderingDomainToUI(domainList)
                }
                .catch { exception ->
                    Log.e(TAG, "Error in settingDomain: $exception")
                }
                .collectLatest { uiSettingList ->
                    updateState {
                        it.copy(
                            settingOrdering = SettingOrderingState.Success(uiSettingList)
                        )
                    }
                }
        }
    }

    fun setIsReadBookWhenLaunch(value: Boolean) = viewModelScope.launch(dispatcher.io) {
        settingRepository.setIsReadBookWhenLaunch(value)
    }

    fun setIsEffect3d(value: Boolean) = viewModelScope.launch(dispatcher.io) {
        settingRepository.setIsEffect3d(value)
    }

    fun setIsShakeToNext(value: Boolean) = viewModelScope.launch(dispatcher.io) {
        settingRepository.setIsShakeToNext(value)
    }

    companion object {
        private const val TAG = "SettingViewModel"
    }
}
