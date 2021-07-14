package com.bignerdranch.android.petsaveapp.core.di

import com.bignerdranch.android.petsaveapp.core.utils.CoroutineDispatchersProvider
import com.bignerdranch.android.petsaveapp.core.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class ApplicationModule {

  @Binds
  abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
          DispatchersProvider
}