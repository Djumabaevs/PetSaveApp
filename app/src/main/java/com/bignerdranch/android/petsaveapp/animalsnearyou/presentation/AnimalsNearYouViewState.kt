package com.bignerdranch.android.petsaveapp.animalsnearyou.presentation

import com.bignerdranch.android.petsaveapp.common.presentation.Event
import com.bignerdranch.android.petsaveapp.common.presentation.model.UIAnimal


data class AnimalsNearYouViewState(
    val loading: Boolean = true,
    val animals: List<UIAnimal> = emptyList(),
    val noMoreAnimalsNearby: Boolean = false,
    val failure: Event<Throwable>? = null
)