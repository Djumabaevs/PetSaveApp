package com.bignerdranch.android.petsaveapp.common.data

import com.bignerdranch.android.petsaveapp.common.data.api.PetFinderApi
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiAnimalMapper
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiPaginationMapper
import com.bignerdranch.android.petsaveapp.common.data.cache.Cache
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedorganization.CachedOrganization
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.Age
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.AnimalWithDetails
import com.bignerdranch.android.petsaveapp.common.domain.model.pagination.PaginatedAnimals
import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchParameters
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchResults
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
        val(apiAnimals, apiPagination) = api.getNearbyAnimals(
            pageToLoad,
            numberOfItems,
            postcode,
            maxDistanceMiles
        )
        return PaginatedAnimals(
            apiAnimals?.map {
                apiAnimalMapper.mapToDomain(it)
            }.orEmpty(),
            apiPaginationMapper.mapToDomain(apiPagination)
        )
    }

    private val postcode = "07097"
    private val maxDistanceMiles = 100

    override suspend fun storeAnimals(animals: List<AnimalWithDetails>) {
        val organizations = animals.map {
            CachedOrganization.fromDomain(it.details.organization)
        }
        cache.storeOrganizations(organizations)
        cache.storeNearbyAnimals(animals.map { CachedAnimalAggregate.fromDomain(it) })
    }

    override suspend fun getAnimalTypes(): List<String> {
        return cache.getAllTypes()
    }

    override fun getAnimalAges(): List<Age> {
        return Age.values().toList()
    }

    override fun searchCachedAnimalsBy(searchParameters: SearchParameters): Flowable<SearchResults> {
        val (name, age, type) = searchParameters
        return cache.searchAnimalsBy(name, age, type)
            .distinctUntilChanged().map { animalList ->
                animalList.map { it.animal.toAnimalDomain(it.photos, it.videos, it.tags) }
            }
            .map{ SearchResults(it, searchParameters) }
    }

    override suspend fun searchAnimalsRemotely(
        pageToLoad: Int,
        searchParameters: SearchParameters,
        numberOfItems: Int
    ): PaginatedAnimals {
        val (apiAnimals, apiPagination) = api.searchAnimalsBy(
            searchParameters.name,
            searchParameters.age,
            searchParameters.type,
            pageToLoad,
            numberOfItems,
            postcode,
            maxDistanceMiles
        )

        return PaginatedAnimals(
            apiAnimals?.map { apiAnimalMapper.mapToDomain(it) }.orEmpty(),
            apiPaginationMapper.mapToDomain(apiPagination)
        )
    }
}