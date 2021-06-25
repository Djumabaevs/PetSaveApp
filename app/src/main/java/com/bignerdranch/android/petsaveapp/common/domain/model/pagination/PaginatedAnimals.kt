package com.bignerdranch.android.petsaveapp.common.domain.model.pagination

import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.AnimalWithDetails

data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)