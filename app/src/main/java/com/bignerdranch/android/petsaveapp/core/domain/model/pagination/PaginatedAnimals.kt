package com.bignerdranch.android.petsaveapp.core.domain.model.pagination


data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)