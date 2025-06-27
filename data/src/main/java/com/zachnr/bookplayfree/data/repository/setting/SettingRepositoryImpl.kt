package com.zachnr.bookplayfree.data.repository.setting

import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.domain.repository.firebase.IFirebaseConfigUpdate
import com.zachnr.bookplayfree.domain.repository.setting.SettingRepository
import com.zachnr.bookplayfree.firebase.RemoteConfigDataSource
import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import com.zachnr.bookplayfree.utils.utils.tryCatchAndReturn
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl(
    private val remoteConfigRepo: RemoteConfigDataSource
): SettingRepository, IFirebaseConfigUpdate {

    override val firebaseRcEffect: Flow<FirebaseEffect> = remoteConfigRepo.effect

    override suspend fun getSettingMenuOrdering(): DomainWrapper<SettingOrderingDomain> {
        // TODO: Handle error message to be delivered, not using empty
        return tryCatchAndReturn(DomainWrapper.Error()) {
            // TODO: Add RC key
            val orderingRc = remoteConfigRepo.getObject(
                "",
                SettingOrderingDomain.serializer(),
                SettingOrderingDomain()
            )
            return@tryCatchAndReturn DomainWrapper.Success(orderingRc)
        }
    }

    override suspend fun initRCConfigUpdateListener() =
        remoteConfigRepo.registerConfigUpdateListener()


    override suspend fun removeRCConfigUpdateListener() =
        remoteConfigRepo.unregisterConfigUpdateListener()
}
