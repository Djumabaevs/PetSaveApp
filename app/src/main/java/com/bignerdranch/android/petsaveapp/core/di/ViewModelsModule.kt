package com.bignerdranch.android.petsaveapp.core.di


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ViewModelsModule {

  @Binds
  abstract fun bindAnimalRepository(repository: PetFinderAnimalRepository): AnimalRepository

  companion object {
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
  }
}