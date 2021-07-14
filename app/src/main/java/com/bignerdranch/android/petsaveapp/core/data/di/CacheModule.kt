package com.bignerdranch.android.petsaveapp.core.data.di


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