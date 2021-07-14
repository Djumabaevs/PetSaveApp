package com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal

@Entity(tableName = "tags")
data class CachedTag(
    @PrimaryKey(autoGenerate = false)
    val tag: String
)