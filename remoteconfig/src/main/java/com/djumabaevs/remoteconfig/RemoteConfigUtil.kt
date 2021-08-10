package com.djumabaevs.remoteconfig

object RemoteConfigUtil {
    private val DEFAULTS: HashMap<String, Any> = hashMapOf()
    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init(debug: Boolean = false) {
        remoteConfig = getFirebaseRemoteConfig(debug)
    }

    private fun getFirebaseRemoteConfig(debug: Boolean):
            FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            if (debug) {
                minimumFetchIntervalInSeconds = 0
            } else {
                minimumFetchIntervalInSeconds = 60 * 60
            }
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(DEFAULTS)
        remoteConfig.fetchAndActivate()
        return remoteConfig
    }

}