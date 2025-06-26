package com.zachnr.bookplayfree.firebase

import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.ConfigUpdateListenerRegistration
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import com.zachnr.bookplayfree.utils.utils.parseJson
import com.zachnr.bookplayfree.utils.utils.tryCatchAndReturn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer

//TODO: Add remote config default value
class RemoteConfigRepositoryImpl(
    private val remoteConfig: FirebaseRemoteConfig,
    private val dispatcher: DispatcherProvider
) : RemoteConfigRepository {

    private val _effects = Channel<FirebaseEffect>()
    override val effect = _effects.receiveAsFlow()
    private var registration: ConfigUpdateListenerRegistration? = null
    private val executedActions = mutableListOf<String>()

    override suspend fun <T> getObject(
        key: String,
        serializer: KSerializer<T>,
        default: T
    ): T = tryCatchAndReturn(default) {
        val objString = remoteConfig.getString(key)
        parseJson(objString, serializer)
    }


    override suspend fun getBoolean(key: String): Boolean = tryCatchAndReturn(false) {
        remoteConfig.getBoolean(key)
    }


    override suspend fun registerConfigUpdateListener() {
        // Make sure only initiated once
        if (registration == null) {
            registration = remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
                override fun onUpdate(configUpdate: ConfigUpdate) {
                    emitEffectFromUpdatedKeys(configUpdate.updatedKeys)
                }

                override fun onError(error: FirebaseRemoteConfigException) {
                    emitEffectError(error.message)
                }
            })
        }
    }

    private fun emitEffectFromUpdatedKeys(keys: Set<String>) {
        // TODO: Review te, ini best practice nya gmn ya ???
        CoroutineScope(dispatcher.io).launch {
            executedActions.forEach {
                if (keys.contains(it)) {
                    _effects.send(FirebaseEffect.OnConfigUpdate(it))
                }
            }
        }
    }

    private fun emitEffectError(message: String?) {
        CoroutineScope(dispatcher.io).launch {
            _effects.send(FirebaseEffect.OnConfigError(message))
        }
    }

    override suspend fun unregisterConfigUpdateListener() {
        registration?.remove()
    }
}
