package com.bignerdranch.android.petsaveapp.animalsnearyou.domain.usecases

import com.bignerdranch.android.petsaveapp.core.domain.model.NoMoreAnimalsException
import com.bignerdranch.android.petsaveapp.core.domain.model.pagination.Pagination
import com.bignerdranch.android.petsaveapp.core.domain.model.pagination.Pagination.Companion.DEFAULT_PAGE_SIZE
import com.bignerdranch.android.petsaveapp.core.domain.repositories.AnimalRepository
import javax.inject.Inject


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