package com.bignerdranch.android.petsaveapp.common.data.cache

import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedorganization.CachedOrganization
import io.reactivex.Flowable

interface Cache {
    suspend fun storeOrganizations(organizations: List<CachedOrganization>)

    fun getNearbyAnimals(): Flowable<List<CachedAnimalAggregate>>
    suspend fun storeNearbyAnimals(animals: List<CachedAnimalAggregate>)
}