package com.bignerdranch.android.petsaveapp.core.domain.model.pagination

import com.bignerdranch.android.petsaveapp.core.domain.model.animal.AnimalWithDetails


data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)