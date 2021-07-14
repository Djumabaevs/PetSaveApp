package com.bignerdranch.android.petsaveapp.core.data.cache.daos

@Dao
abstract class AnimalsDao {

  @Transaction
  @Query("SELECT * FROM animals ORDER BY animalId DESC")
  abstract fun getAllAnimals(): Flowable<List<CachedAnimalAggregate>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  abstract fun insertAnimalAggregate(
      animal: CachedAnimalWithDetails,
      photos: List<CachedPhoto>,
      videos: List<CachedVideo>,
      tags: List<CachedTag>
  )

  fun insertAnimalsWithDetails(animalAggregates: List<CachedAnimalAggregate>) {
    for (animalAggregate in animalAggregates) {
      insertAnimalAggregate(
          animalAggregate.animal,
          animalAggregate.photos,
          animalAggregate.videos,
          animalAggregate.tags
      )
    }
  }

  @Query("SELECT DISTINCT type FROM animals")
  abstract suspend fun getAllTypes(): List<String>

  @Transaction
  @Query("""
      SELECT * FROM animals 
        WHERE upper(name) LIKE '%' || :name || '%' AND 
        AGE LIKE '%' || :age || '%' 
        AND type LIKE '%' || :type || '%'
  """)
  abstract fun searchAnimalsBy(
      name: String,
      age: String,
      type: String
  ): Flowable<List<CachedAnimalAggregate>>
}
