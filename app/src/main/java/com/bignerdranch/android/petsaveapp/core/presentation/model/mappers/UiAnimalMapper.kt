package com.bignerdranch.android.petsaveapp.core.presentation.model.mappers

import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.core.presentation.model.UIAnimal
import javax.inject.Inject

class UiAnimalMapper @Inject constructor(): UiMapper<Animal, UIAnimal> {

  override fun mapToView(input: Animal): UIAnimal {
    return UIAnimal(
        id = input.id,
        name = input.name,
        photo = input.media.getFirstSmallestAvailablePhoto()
    )
  }
}
