package com.bignerdranch.android.petsaveapp.common.data.di

import com.bignerdranch.android.petsaveapp.common.data.cache.PetSaveDatabase
import com.bignerdranch.android.petsaveapp.common.data.cache.daos.OrganizationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    companion object {

        @Provides
        @Singleton


        @Provides
        fun provideOrganizationsDao(petSaveDatabase: PetSaveDatabase): OrganizationsDao =
            petSaveDatabase.organizationsDao()
    }
}