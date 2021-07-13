package com.djumabaevs.sharing.presentation

import com.djumabaevs.sharing.presentation.model.UIAnimalToShare


data class SharingViewState(
    val animalToShare: UIAnimalToShare = UIAnimalToShare(image = "", defaultMessage = "")
)