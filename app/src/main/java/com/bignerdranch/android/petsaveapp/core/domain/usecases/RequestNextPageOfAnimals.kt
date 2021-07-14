package com.bignerdranch.android.petsaveapp.core.domain.usecases


class RequestNextPageOfAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
){

  suspend operator fun invoke(pageToLoad: Int, pageSize: Int = DEFAULT_PAGE_SIZE): Pagination {
    val (animals, pagination) = animalRepository.requestMoreAnimals(pageToLoad, pageSize)

    if (animals.isEmpty()) throw NoMoreAnimalsException("No animals nearby :(")

    animalRepository.storeAnimals(animals)

    return pagination
  }
}