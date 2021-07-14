package com.bignerdranch.android.petsaveapp.search.presentation

import com.bignerdranch.android.petsaveapp.core.presentation.Event
import com.bignerdranch.android.petsaveapp.core.presentation.model.UIAnimal


data class SearchViewState(
    val noSearchQueryState: Boolean = true,
    val searchResults: List<UIAnimal> = emptyList(),
    val ageMenuValues: Event<List<String>> = Event(emptyList()),
    val typeMenuValues: Event<List<String>> = Event(emptyList()),
    val searchingRemotely: Boolean = false,
    val noResultsState: Boolean = false,
    val failure: Event<Throwable>? = null
)
