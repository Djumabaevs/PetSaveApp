package com.bignerdranch.android.petsaveapp.common.data.cache.daos

import androidx.room.*
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedanimal.*
import io.reactivex.Flowable

@Dao
abstract class AnimalsDao {

    @Transaction
    @Query("SELECT * FROM animals")
    abstract fun getAllAnimals(): Flowable<List<CachedAnimalAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAnimalAggregate(
        animal: CachedAnimalWithDetails,
        photos: List<CachedPhoto>,
        video: List<CachedVideo>,
        tags: List<CachedTag>
    )

}