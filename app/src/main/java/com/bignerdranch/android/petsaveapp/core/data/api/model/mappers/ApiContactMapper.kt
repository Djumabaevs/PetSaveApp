package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

class ApiContactMapper @Inject constructor(
    private val apiAddressMapper: ApiAddressMapper
): ApiMapper<ApiContact?, Organization.Contact> {

  override fun mapToDomain(apiEntity: ApiContact?): Organization.Contact {
    return Organization.Contact(
        email = apiEntity?.email.orEmpty(),
        phone = apiEntity?.phone.orEmpty(),
        address = apiAddressMapper.mapToDomain(apiEntity?.address)
    )
  }
}