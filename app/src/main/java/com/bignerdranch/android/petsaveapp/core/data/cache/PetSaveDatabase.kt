package com.bignerdranch.android.petsaveapp.core.data.cache


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