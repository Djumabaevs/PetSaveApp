package com.bignerdranch.android.petsaveapp.common.data.di

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.bignerdranch.android.petsaveapp.core.data.cache.Cache
import com.bignerdranch.android.petsaveapp.core.data.cache.PetSaveDatabase
import com.bignerdranch.android.petsaveapp.core.data.cache.RoomCache
import com.bignerdranch.android.petsaveapp.core.data.cache.daos.AnimalsDao
import com.bignerdranch.android.petsaveapp.core.data.cache.daos.OrganizationsDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
abstract class TestCacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideRoomDatabase(): PetSaveDatabase {
            return Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().context,
                    PetSaveDatabase::class.java
            )
                    .allowMainThreadQueries()
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