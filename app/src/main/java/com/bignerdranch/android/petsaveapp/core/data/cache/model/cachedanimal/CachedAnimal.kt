package com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal

import com.bignerdranch.android.petsaveapp.core.domain.model.animal.AdoptionStatus
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Media
import com.bignerdranch.android.petsaveapp.core.utils.DateTimeUtils

data class CachedAnimal(
    val animalId: Long,
    val name: String,
    val type: String,
    val adoptionStatus: String,
    val publishedAt: String
) {

  companion object {
    fun fromDomain(animal: Animal): CachedAnimal {
      return CachedAnimal(
          animal.id,
          animal.name,
          animal.type,
          animal.adoptionStatus.toString(),
          animal.publishedAt.toString()
      )
    }
  }

  fun toDomain(photos: List<CachedPhoto>, videos: List<CachedVideo>, tags: List<CachedTag>): Animal {
    return Animal(
        animalId,
        name,
        type,
        Media(
            photos = photos.map { it.toDomain() },
            videos = videos.map { it.toDomain() }
        ),
        tags.map { it.tag },
        AdoptionStatus.valueOf(adoptionStatus),
        DateTimeUtils.parse(publishedAt)
    )
  }
}
