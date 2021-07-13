package com.bignerdranch.android.petsaveapp.common.di

import com.bignerdranch.android.petsaveapp.common.data.api.PetFinderApi
import com.bignerdranch.android.petsaveapp.common.data.cache.Cache
import com.bignerdranch.android.petsaveapp.common.data.preferences.Preferences
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SharingModuleDependencies {
    fun petFinderApi(): PetFinderApi
    fun cache(): Cache
    fun preferences(): Preferences
}