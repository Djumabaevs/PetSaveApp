package com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal


@Entity(
    tableName = "photos",
    foreignKeys = [
      ForeignKey(
          entity = CachedAnimalWithDetails::class,
          parentColumns = ["animalId"],
          childColumns = ["animalId"],
          onDelete = ForeignKey.CASCADE
      )
    ],
    indices = [Index("animalId")]
)
data class CachedPhoto(
    @PrimaryKey(autoGenerate = true)
    val photoId: Long = 0,
    val animalId: Long,
    val medium: String,
    val full: String
) {
  companion object {
    fun fromDomain(animalId: Long, photo: Media.Photo): CachedPhoto {
      val (medium, full) = photo

      return CachedPhoto(animalId, animalId, medium, full)
    }
  }

  fun toDomain(): Media.Photo = Media.Photo(medium, full)
}