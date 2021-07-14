package com.bignerdranch.android.petsaveapp.core.di


@Module
@InstallIn(ApplicationComponent::class)
abstract class ApplicationModule {

  @Binds
  abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
      DispatchersProvider
}