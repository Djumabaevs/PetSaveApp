package com.bignerdranch.android.petsaveapp.core.presentation.model.mappers

class UiAnimalMapper @Inject constructor(): UiMapper<Animal, UIAnimal> {

  override fun mapToView(input: Animal): UIAnimal {
    return UIAnimal(
        id = input.id,
        name = input.name,
        photo = input.media.getFirstSmallestAvailablePhoto()
    )
  }
}
