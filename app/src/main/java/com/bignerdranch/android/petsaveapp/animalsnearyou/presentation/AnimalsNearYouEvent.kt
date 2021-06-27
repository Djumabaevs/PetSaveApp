package com.bignerdranch.android.petsaveapp.animalsnearyou.presentation

sealed class AnimalsNearYouEvent {
    object RequestInitialAnimalsList: AnimalsNearYouEvent()
    object RequestMoreAnimals: AnimalsNearYouEvent()
}