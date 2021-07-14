package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

class ApiHealthDetailsMapper @Inject constructor():
    ApiMapper<ApiAttributes?, AnimalWithDetails.Details.HealthDetails> {

  override fun mapToDomain(apiEntity: ApiAttributes?): AnimalWithDetails.Details.HealthDetails {
    return AnimalWithDetails.Details.HealthDetails(
        isSpayedOrNeutered = apiEntity?.spayedNeutered ?: false,
        isDeclawed = apiEntity?.declawed ?: false,
        hasSpecialNeeds = apiEntity?.specialNeeds ?: false,
        shotsAreCurrent = apiEntity?.shotsCurrent ?: false
    )
  }
}
