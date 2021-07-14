package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

class ApiVideoMapper @Inject constructor(): ApiMapper<ApiVideoLink?, Media.Video> {

  override fun mapToDomain(apiEntity: ApiVideoLink?): Media.Video {
    return Media.Video(video = apiEntity?.embed.orEmpty())
  }
}
