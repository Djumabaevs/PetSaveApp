package com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal


@Entity(
    tableName = "videos",
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
data class CachedVideo(
    @PrimaryKey(autoGenerate = true)
    val videoId: Long = 0,
    val animalId: Long,
    val video: String
) {
  companion object {
    fun fromDomain(animalId: Long, video: Media.Video): CachedVideo {
      return CachedVideo(animalId = animalId, video = video.video)
    }
  }

  fun toDomain(): Media.Video = Media.Video(video)
}
