package com.bignerdranch.android.petsaveapp.onboarding.usecases

import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class OnboardingIsComplete @Inject constructor(
    private val repository: AnimalRepository
) {
    suspend operator fun invoke() = repository.onboardingIsComplete()
}