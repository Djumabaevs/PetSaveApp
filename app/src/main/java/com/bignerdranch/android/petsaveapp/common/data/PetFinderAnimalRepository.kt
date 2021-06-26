package com.bignerdranch.android.petsaveapp.common.data

import com.bignerdranch.android.petsaveapp.common.data.api.PetFinderApi
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiAnimalMapper
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiPaginationMapper
import com.bignerdranch.android.petsaveapp.common.data.cache.Cache
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.AnimalWithDetails
import com.bignerdranch.android.petsaveapp.common.domain.model.pagination.PaginatedAnimals
import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class PetFinderAnimalRepository @Inject constructor(
    private val api: PetFinderApi,
    private val cache: Cache,
    private val apiAnimalMapper: ApiAnimalMapper,
    private val apiPaginationMapper: ApiPaginationMapper
): AnimalRepository {
    override fun getAnimals(): Flowable<List<Animal>> {
        return cache.getNearbyAnimals()
            .distinctUntilChanged()
            .map { animalList ->
                animalList.map {
                    it.animal.toAnimalDomain(
                        it.photos,
                        it.videos,
                        it.tags
                    )
                }
            }
    }

    override suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals {

    }

    override suspend fun storeAnimals(animals: List<AnimalWithDetails>) {
        TODO("Not yet implemented")
    }
}