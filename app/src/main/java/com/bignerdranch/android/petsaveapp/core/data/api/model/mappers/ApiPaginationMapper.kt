package com.bignerdranch.android.petsaveapp.core.data.api.model.mappers

class ApiPaginationMapper @Inject constructor(): ApiMapper<ApiPagination?, Pagination> {

  override fun mapToDomain(apiEntity: ApiPagination?): Pagination {
    return Pagination(
        currentPage = apiEntity?.currentPage ?: 0,
        totalPages = apiEntity?.totalPages ?: 0
    )
  }
}