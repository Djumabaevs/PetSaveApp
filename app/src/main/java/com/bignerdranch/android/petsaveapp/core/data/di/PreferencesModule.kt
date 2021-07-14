package com.bignerdranch.android.petsaveapp.core.data.di


@Module
@InstallIn(ApplicationComponent::class)
abstract class PreferencesModule {

  @Binds
  abstract fun providePreferences(preferences: PetSavePreferences): Preferences
}