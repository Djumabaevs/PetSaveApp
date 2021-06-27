package com.bignerdranch.android.petsaveapp.common.presentation.model.mappers

interface UiMapper<E, V> {

    fun mapToView(input: E): V
}