package com.bignerdranch.android.petsaveapp.details.presentation

sealed class AnimalDetailsEvent {
    data class LoadAnimalDetails(val animalId: Long): AnimalDetailsEvent()
    object AdoptAnimal: AnimalDetailsEvent()
}