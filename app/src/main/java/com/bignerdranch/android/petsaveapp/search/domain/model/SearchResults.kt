package com.bignerdranch.android.petsaveapp.search.domain.model

import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Animal


data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)