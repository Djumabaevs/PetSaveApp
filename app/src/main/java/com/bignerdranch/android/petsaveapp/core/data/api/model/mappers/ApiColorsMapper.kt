package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

class ApiColorsMapper @Inject constructor():
    ApiMapper<ApiColors?, AnimalWithDetails.Details.Colors> {

  override fun mapToDomain(apiEntity: ApiColors?): AnimalWithDetails.Details.Colors {
    return AnimalWithDetails.Details.Colors(
        primary = apiEntity?.primary.orEmpty(),
        secondary = apiEntity?.secondary.orEmpty(),
        tertiary = apiEntity?.tertiary.orEmpty()
    )
  }
}
