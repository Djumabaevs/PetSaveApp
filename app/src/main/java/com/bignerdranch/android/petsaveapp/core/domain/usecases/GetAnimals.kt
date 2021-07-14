package com.bignerdranch.android.petsaveapp.core.domain.usecases


class GetAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
) {

  operator fun invoke() = animalRepository.getAnimals()
      .filter { it.isNotEmpty() }
}