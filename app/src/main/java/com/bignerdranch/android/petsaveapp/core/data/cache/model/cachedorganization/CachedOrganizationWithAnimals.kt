package com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedorganization


data class CachedOrganizationWithAnimals(
    @Embedded val organization: CachedOrganization,
    @Relation(
        entity = CachedAnimalWithDetails::class,
        parentColumn = "id",
        entityColumn = "organizationId"
    )
    val animals: List<CachedAnimalAggregate>
)