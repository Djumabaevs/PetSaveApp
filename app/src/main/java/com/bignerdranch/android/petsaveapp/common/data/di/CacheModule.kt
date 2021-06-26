package com.bignerdranch.android.petsaveapp.common.data.di

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.petsaveapp.common.data.cache.PetSaveDatabase
import com.bignerdranch.android.petsaveapp.common.data.cache.daos.AnimalsDao
import com.bignerdranch.android.petsaveapp.common.data.cache.daos.OrganizationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): PetSaveDatabase {
            return Room.databaseBuilder(
                context,
                PetSaveDatabase::class.java,
                "petsave.db"
            )
                .build()
        }

        @Provides
        fun provideAnimalsDao(
            petSaveDatabase: PetSaveDatabase
        ): AnimalsDao = petSaveDatabase.animalsDao()


        @Provides
        fun provideOrganizationsDao(petSaveDatabase: PetSaveDatabase): OrganizationsDao =
            petSaveDatabase.organizationsDao()
    }
}