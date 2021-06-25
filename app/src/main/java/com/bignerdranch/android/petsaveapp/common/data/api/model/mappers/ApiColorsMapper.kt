package com.bignerdranch.android.petsaveapp.common.data.api.model.mappers

import com.bignerdranch.android.petsaveapp.common.data.api.model.ApiColors
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.details.Colors
import javax.inject.Inject

class ApiColorsMapper @Inject constructor(): ApiMapper<ApiColors?, Colors> {

    override fun mapToDomain(apiEntity: ApiColors?): Colors {
        return Colors(
            primary = apiEntity?.primary.orEmpty(),
            secondary = apiEntity?.secondary.orEmpty(),
            tertiary = apiEntity?.tertiary.orEmpty()
        )
    }
}