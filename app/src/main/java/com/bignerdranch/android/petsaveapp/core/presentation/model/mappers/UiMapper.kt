package com.bignerdranch.android.petsaveapp.core.presentation.model.mappers


interface UiMapper<E, V> {

  fun mapToView(input: E): V
}