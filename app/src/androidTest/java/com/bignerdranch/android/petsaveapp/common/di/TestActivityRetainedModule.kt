package com.bignerdranch.android.petsaveapp.common.di

import com.bignerdranch.android.petsaveapp.common.data.FakeRepository
import com.bignerdranch.android.petsaveapp.core.domain.repositories.AnimalRepository
import com.bignerdranch.android.petsaveapp.core.utils.CoroutineDispatchersProvider
import com.bignerdranch.android.petsaveapp.core.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class TestActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAnimalRepository(repository: FakeRepository): AnimalRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
            DispatchersProvider

    companion object {
        @Provides
        fun provideCompositeDisposable() = CompositeDisposable()
    }
}