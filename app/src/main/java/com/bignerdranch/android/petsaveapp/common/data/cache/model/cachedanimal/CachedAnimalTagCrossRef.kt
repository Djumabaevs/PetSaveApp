package com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedanimal

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["tag", "animalId"],
    indices = [Index("animalId")]
)
data class CachedAnimalTagCrossRef(
    val animalId: Long,
    val tag: String
)