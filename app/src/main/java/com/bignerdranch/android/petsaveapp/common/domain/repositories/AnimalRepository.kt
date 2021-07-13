package com.bignerdranch.android.petsaveapp.common.domain.repositories

import com.bignerdranch.android.petsaveapp.common.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.Age
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.AnimalWithDetails
import com.bignerdranch.android.petsaveapp.common.domain.model.pagination.PaginatedAnimals
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchParameters
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchResults
import io.reactivex.Flowable

interface AnimalRepository {
    fun getAnimals(): Flowable<List<Animal>>
    suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals
    suspend fun storeAnimals(animals: List<AnimalWithDetails>)
    suspend fun getAnimalTypes(): List<String>
    suspend fun getAnimal(animalId: Long): AnimalWithDetails
    fun getAnimalAges(): List<Age>
    fun searchCachedAnimalsBy(searchParameters: SearchParameters): Flowable<SearchResults>
    suspend fun searchAnimalsRemotely(
        pageToLoad: Int,
        searchParameters: SearchParameters,
        numberOfItems: Int
    ): PaginatedAnimals
}