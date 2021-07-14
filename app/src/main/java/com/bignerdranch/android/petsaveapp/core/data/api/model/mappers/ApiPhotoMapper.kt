package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

class ApiPhotoMapper @Inject constructor(): ApiMapper<ApiPhotoSizes?, Media.Photo> {

  override fun mapToDomain(apiEntity: ApiPhotoSizes?): Media.Photo {
    return Media.Photo(
        medium = apiEntity?.medium.orEmpty(),
        full = apiEntity?.full.orEmpty()
    )
  }
}
