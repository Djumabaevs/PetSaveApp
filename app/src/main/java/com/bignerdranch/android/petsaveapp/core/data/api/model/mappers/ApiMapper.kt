package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

interface ApiMapper<E, D> {

  fun mapToDomain(apiEntity: E): D
}