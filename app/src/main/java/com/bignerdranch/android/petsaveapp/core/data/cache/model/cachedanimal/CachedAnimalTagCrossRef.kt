package com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal


@Entity(primaryKeys = ["animalId", "tag"], indices = [Index("tag")])
data class CachedAnimalTagCrossRef(
    val animalId: Long,
    val tag: String
)