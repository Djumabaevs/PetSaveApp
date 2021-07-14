package com.djumabaevs.sharing.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.petsaveapp.common.data.PetFinderAnimalRepository
import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import com.bignerdranch.android.petsaveapp.common.utils.CoroutineDispatchersProvider
import com.bignerdranch.android.petsaveapp.common.utils.DispatchersProvider
import com.djumabaevs.sharing.presentation.SharingFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@Module
@DisableInstallInCheck
abstract class SharingModule {

  // These two are not scoped to SingletonComponent, so they can't be directly provided through
  // methods in SharingModuleDependencies.
  @Binds
  abstract fun bindDispatchersProvider(
      dispatchersProvider: CoroutineDispatchersProvider
  ): DispatchersProvider

  @Binds
  abstract fun bindRepository(repository: PetFinderAnimalRepository): AnimalRepository

  @Binds
  @IntoMap
  @ViewModelKey(SharingFragmentViewModel::class)
  abstract fun bindSharingFragmentViewModel(
      sharingFragmentViewModel: SharingFragmentViewModel
  ): ViewModel

  @Binds
  @Reusable
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}