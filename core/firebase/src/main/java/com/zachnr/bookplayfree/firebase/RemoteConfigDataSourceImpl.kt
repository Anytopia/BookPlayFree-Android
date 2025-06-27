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
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer

class RemoteConfigDataSourceImpl(
    private val remoteConfig: FirebaseRemoteConfig,
    dispatcher: DispatcherProvider
) : RemoteConfigDataSource {

    private val _effects = Channel<FirebaseEffect>()
    override val effect = _effects.receiveAsFlow()
    private var registration: ConfigUpdateListenerRegistration? = null
    // This variable is to store the RC key that has been retrieved. The key then used again
    // to notify the presentation layer and selectively hit only the updated keys.
    private val executedKeys = mutableSetOf<String>()
    private val scope = CoroutineScope(SupervisorJob() + dispatcher.io)

    override suspend fun <T> getObject(
        key: String,
        serializer: KSerializer<T>,
        default: T
    ): T = tryCatchAndReturn(default) {
        val objString = remoteConfig.getString(key)
        parseJson(objString, serializer).also {
            executedKeys.add(key)
        }
    }

    override suspend fun getBoolean(key: String): Boolean = tryCatchAndReturn(false) {
        remoteConfig.getBoolean(key).also {
            executedKeys.add(key)
        }
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
        scope.launch {
            executedKeys.forEach {
                if (keys.contains(it)) {
                    _effects.send(FirebaseEffect.OnConfigUpdate(it))
                }
            }
        }
    }

    private fun emitEffectError(message: String?) {
        scope.launch {
            _effects.send(FirebaseEffect.OnConfigError(message))
        }
    }

    override suspend fun unregisterConfigUpdateListener() {
        registration?.remove()
        registration = null
        scope.cancel()
    }
}
