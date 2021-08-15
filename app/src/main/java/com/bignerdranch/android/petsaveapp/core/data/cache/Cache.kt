package com.bignerdranch.android.petsaveapp.core.data.cache

import com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedorganization.CachedOrganization
import io.reactivex.Flowable
import io.reactivex.Single

interface Cache {
    fun getNearbyAnimals(): Flowable<List<CachedAnimalAggregate>>
    fun storeOrganizations(organizations: List<CachedOrganization>)
    fun getOrganization(organizationId: String): Single<CachedOrganization>
    fun storeNearbyAnimals(animals: List<CachedAnimalAggregate>)
    suspend fun getAllTypes(): List<String>

    fun searchAnimalsBy(
        nameOrBreed: String,
        age: String,
        type: String
    ): Flowable<List<CachedAnimalAggregate>>

    fun getAnimal(animalId: Long): Single<CachedAnimalAggregate>
}