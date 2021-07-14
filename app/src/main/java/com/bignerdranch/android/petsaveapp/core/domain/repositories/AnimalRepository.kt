package com.bignerdranch.android.petsaveapp.core.domain.repositories


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
}