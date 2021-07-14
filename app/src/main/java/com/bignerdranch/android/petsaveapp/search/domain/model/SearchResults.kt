package com.bignerdranch.android.petsaveapp.search.domain.model


data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)