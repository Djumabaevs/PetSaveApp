package com.bignerdranch.android.petsaveapp.details.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.petsaveapp.core.presentation.model.mappers.UiAnimalDetailsMapper
import com.bignerdranch.android.petsaveapp.details.domain.usecases.AnimalDetails
import io.reactivex.disposables.CompositeDisposable

class AnimalDetailsViewModel @ViewModelInject constructor(
    private val uiAnimalDetailsMapper: UiAnimalDetailsMapper,
    private val animalDetails: AnimalDetails,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {
}