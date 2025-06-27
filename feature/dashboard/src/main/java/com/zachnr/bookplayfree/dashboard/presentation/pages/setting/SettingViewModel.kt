package com.zachnr.bookplayfree.dashboard.presentation.pages.setting

import androidx.lifecycle.viewModelScope
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.mapper.SettingMapper
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingState
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.repository.setting.SettingRepository
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent
import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(
    navigator: Navigator,
    private val dispatcher: DispatcherProvider,
    private val settingRepository: SettingRepository,
    private val settingMapper: SettingMapper
) : BaseViewModel<SettingState, ViewEvent, ViewEffect>(
    navigator
) {

    val firebaseEffect = settingRepository.firebaseRcEffect.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = FirebaseEffect.Initial,
    )

    init {
        getSettingOrdering()
        viewModelScope.launch {
            settingRepository.initRCConfigUpdateListener()
        }
    }

    override fun setInitialState(): SettingState = SettingState.Loading

    override fun handleEvents(event: ViewEvent) {}

    fun getSettingOrdering() = viewModelScope.launch(dispatcher.io) {
        when (val orderingDomain = settingRepository.getSettingMenuOrdering()) {
            is DomainWrapper.Success -> {
                val orderingUi = settingMapper.mapSettingOrderingDomainToUI(orderingDomain.data)
                updateState {
                    if (it is SettingState.Success) {
                        it.copy(settingOrder = orderingUi)
                    } else {
                        SettingState.Success(orderingUi)
                    }
                }
            }

            is DomainWrapper.Error -> {
                updateState {
                    SettingState.Error()
                }
            }
        }
    }

    fun removeFirebaseListener() = viewModelScope.launch {
        settingRepository.removeRCConfigUpdateListener()
    }
}
