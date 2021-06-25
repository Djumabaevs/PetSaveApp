package com.bignerdranch.android.petsaveapp.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedanimal.*
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedorganization.CachedOrganization

@Database(
    entities = [
        CachedPhoto::class,
        CachedVideo::class,
        CachedTag::class,
        CachedAnimalWithDetails::class,
        CachedOrganization::class,
        CachedAnimalTagCrossRef::class
    ],
    version = 1
)
abstract class PetSaveDatabase : RoomDatabase() {
//    abstract fun organizationsDao(): OrganizationsDao
}