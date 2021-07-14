package com.bignerdranch.android.petsaveapp.core.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.petsaveapp.core.data.cache.daos.AnimalsDao
import com.bignerdranch.android.petsaveapp.core.data.cache.daos.OrganizationsDao
import com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal.*
import com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedorganization.CachedOrganization


@Database(
    entities = [
      CachedAnimalWithDetails::class,
      CachedPhoto::class,
      CachedVideo::class,
      CachedTag::class,
      CachedAnimalTagCrossRef::class,
      CachedOrganization::class
    ],
    version = 1
)
abstract class PetSaveDatabase : RoomDatabase() {
  abstract fun animalsDao(): AnimalsDao
  abstract fun organizationsDao(): OrganizationsDao
}