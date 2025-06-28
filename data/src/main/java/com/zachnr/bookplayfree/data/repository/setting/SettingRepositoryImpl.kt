package com.zachnr.bookplayfree.data.repository.setting

import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.domain.repository.setting.SettingRepository
import com.zachnr.bookplayfree.firebase.RemoteConfigDataSource
import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import com.zachnr.bookplayfree.utils.utils.FirebaseConst.KEY_SETTING_ORDERING
import com.zachnr.bookplayfree.utils.utils.tryCatchAndReturn
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.builtins.ListSerializer

class SettingRepositoryImpl(
    private val remoteConfigRepo: RemoteConfigDataSource
) : SettingRepository {

    override val firebaseRcEffect: Flow<FirebaseEffect> = remoteConfigRepo.effect

    override suspend fun getSettingMenuOrdering(): DomainWrapper<List<SettingOrderingDomain>> {
        // TODO: Handle error message to be delivered, not using empty
        return tryCatchAndReturn(DomainWrapper.Error()) {
            val orderingRc = remoteConfigRepo.getObject(
                KEY_SETTING_ORDERING,
                ListSerializer(SettingOrderingDomain.serializer()),
                emptyList()
            )
            return@tryCatchAndReturn DomainWrapper.Success(orderingRc)
        }
    }

    override suspend fun initRCConfigUpdateListener() =
        remoteConfigRepo.registerConfigUpdateListener()


    override suspend fun removeRCConfigUpdateListener() =
        remoteConfigRepo.unregisterConfigUpdateListener()
}
