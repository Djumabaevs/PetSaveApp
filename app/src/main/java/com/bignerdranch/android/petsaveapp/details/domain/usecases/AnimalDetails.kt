package com.bignerdranch.android.petsaveapp.details.domain.usecases

import com.bignerdranch.android.petsaveapp.core.domain.model.animal.AnimalWithDetails
import com.bignerdranch.android.petsaveapp.core.domain.repositories.AnimalRepository
import io.reactivex.Single
import javax.inject.Inject

class AnimalDetails @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    operator fun invoke(
        animalId: Long
    ): Single<AnimalWithDetails> {
        return animalRepository.getAnimal(animalId)
    }
}