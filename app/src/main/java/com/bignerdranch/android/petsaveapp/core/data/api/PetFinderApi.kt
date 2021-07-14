package com.bignerdranch.android.petsaveapp.core.data.api

interface PetFinderApi {

  @GET(ApiConstants.ANIMALS_ENDPOINT)
  suspend fun getNearbyAnimals(
      @Query(ApiParameters.PAGE) pageToLoad: Int,
      @Query(ApiParameters.LIMIT) pageSize: Int,
      @Query(ApiParameters.LOCATION) postcode: String,
      @Query(ApiParameters.DISTANCE) maxDistance: Int
  ): ApiPaginatedAnimals

  @GET(ApiConstants.ANIMALS_ENDPOINT)
  suspend fun searchAnimalsBy(
      @Query(ApiParameters.NAME) name: String,
      @Query(ApiParameters.AGE) age: String,
      @Query(ApiParameters.TYPE) type: String,
      @Query(ApiParameters.PAGE) pageToLoad: Int,
      @Query(ApiParameters.LIMIT) pageSize: Int,
      @Query(ApiParameters.LOCATION) postcode: String,
      @Query(ApiParameters.DISTANCE) maxDistance: Int
  ): ApiPaginatedAnimals
}