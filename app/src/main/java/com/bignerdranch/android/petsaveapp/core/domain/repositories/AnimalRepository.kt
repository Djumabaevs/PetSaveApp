package com.bignerdranch.android.petsaveapp.core.domain.repositories

import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.AnimalWithDetails
import com.bignerdranch.android.petsaveapp.core.domain.model.pagination.PaginatedAnimals
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchParameters
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchResults
import io.reactivex.Flowable
import io.reactivex.Single


interface AnimalRepository {

  fun getAnimals(): Flowable<List<Animal>>
  suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals
  suspend fun storeAnimals(animals: List<AnimalWithDetails>)
  suspend fun getAnimalTypes(): List<String>
  fun getAnimalAges(): List<AnimalWithDetails.Details.Age>
  fun searchCachedAnimalsBy(searchParameters: SearchParameters): Flowable<SearchResults>

  suspend fun searchAnimalsRemotely(
      pageToLoad: Int,
      searchParameters: SearchParameters,
      numberOfItems: Int
  ): PaginatedAnimals

    fun getAnimal(
        animalId: Long
    ): Single<AnimalWithDetails>
}