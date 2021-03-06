package com.bignerdranch.android.petsaveapp.core.data.di

import android.content.Context
import androidx.room.Room
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
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
abstract class CacheModule {

  @Binds
  abstract fun bindCache(cache: RoomCache): Cache

  companion object {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PetSaveDatabase {
      return Room.databaseBuilder(context, PetSaveDatabase::class.java, "petsave.db")
          .build()
    }

    @Provides
    fun provideAnimalsDao(petSaveDatabase: PetSaveDatabase): AnimalsDao =
        petSaveDatabase.animalsDao()

    @Provides
    fun provideOrganizationsDao(petSaveDatabase: PetSaveDatabase): OrganizationsDao =
        petSaveDatabase.organizationsDao()
  }
}