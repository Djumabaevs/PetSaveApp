package com.bignerdranch.android.petsaveapp.animalsnearyou.domain.usecases

import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.AnimalWithDetails
import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class GetAnimalDetails @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    suspend operator fun invoke(animalId: Long): AnimalWithDetails {
        return animalRepository.getAnimal(animalId)
    }
}