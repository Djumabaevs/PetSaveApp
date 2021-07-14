package com.bignerdranch.android.petsaveapp.core.data.cache

class RoomCache @Inject constructor(
    private val animalsDao: AnimalsDao,
    private val organizationsDao: OrganizationsDao
): Cache {

  override fun getNearbyAnimals(): Flowable<List<CachedAnimalAggregate>> {
    return animalsDao.getAllAnimals()
  }

  override fun storeOrganizations(organizations: List<CachedOrganization>) {
    organizationsDao.insert(organizations)
  }

  override fun storeNearbyAnimals(animals: List<CachedAnimalAggregate>) {
    animalsDao.insertAnimalsWithDetails(animals)
  }

  override suspend fun getAllTypes(): List<String> {
    return animalsDao.getAllTypes()
  }

  override fun searchAnimalsBy(
      nameOrBreed: String,
      age: String,
      type: String
  ): Flowable<List<CachedAnimalAggregate>> {
    return animalsDao.searchAnimalsBy(nameOrBreed, age, type)
  }
}