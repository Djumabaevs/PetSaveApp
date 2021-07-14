package com.bignerdranch.android.petsaveapp.onboarding.usecases

import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class StoreOnboardingData @Inject constructor(
    private val repository: AnimalRepository
) {

    suspend operator fun invoke(postcode: String, distance: String) {
        repository.storeOnboardingData(postcode, distance.toInt())
    }
}