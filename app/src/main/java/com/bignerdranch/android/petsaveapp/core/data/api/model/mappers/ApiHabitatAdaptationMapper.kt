package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

class ApiHabitatAdaptationMapper @Inject constructor():
    ApiMapper<ApiEnvironment?, AnimalWithDetails.Details.HabitatAdaptation> {

  override fun mapToDomain(apiEntity: ApiEnvironment?): AnimalWithDetails.Details.HabitatAdaptation {
    return AnimalWithDetails.Details.HabitatAdaptation(
        goodWithChildren = apiEntity?.children ?: true,
        goodWithDogs = apiEntity?.dogs ?: true,
        goodWithCats = apiEntity?.cats ?: true
    )
  }
}
