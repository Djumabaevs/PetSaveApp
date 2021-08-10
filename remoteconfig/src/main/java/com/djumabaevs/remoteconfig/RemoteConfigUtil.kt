package com.djumabaevs.remoteconfig

object RemoteConfigUtil {
    private val DEFAULTS: HashMap<String, Any> = hashMapOf()
    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init(debug: Boolean = false) {
        remoteConfig = getFirebaseRemoteConfig(debug)
    }


}