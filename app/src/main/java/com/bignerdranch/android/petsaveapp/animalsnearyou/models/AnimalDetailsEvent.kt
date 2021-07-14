package com.bignerdranch.android.petsaveapp.animalsnearyou.models

sealed class AnimalDetailsEvent {
    data class LoadAnimalDetails(val animalId: Long) : AnimalDetailsEvent()
}