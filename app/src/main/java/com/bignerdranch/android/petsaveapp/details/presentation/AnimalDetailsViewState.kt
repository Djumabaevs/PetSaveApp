package com.bignerdranch.android.petsaveapp.details.presentation

import com.bignerdranch.android.petsaveapp.core.presentation.model.UIAnimalDetailed

sealed class AnimalDetailsViewState {
    object Loading: AnimalDetailsViewState()

    data class AnimalDetails(
        val animal: UIAnimalDetailed,
        val adopted: Boolean = false
    ): AnimalDetailsViewState()

    object Failure: AnimalDetailsViewState()
}