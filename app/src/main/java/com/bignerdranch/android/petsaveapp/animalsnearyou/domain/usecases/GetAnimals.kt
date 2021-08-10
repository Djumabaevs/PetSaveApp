package com.bignerdranch.android.petsaveapp.animalsnearyou.domain.usecases

import com.bignerdranch.android.petsaveapp.core.domain.repositories.AnimalRepository
import javax.inject.Inject


class GetAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
) {

  operator fun invoke() = animalRepository.getAnimals()
      .filter { it.isNotEmpty() }
}