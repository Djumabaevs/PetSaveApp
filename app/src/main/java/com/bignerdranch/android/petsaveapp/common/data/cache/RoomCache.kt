package com.bignerdranch.android.petsaveapp.common.data.cache

import com.bignerdranch.android.petsaveapp.common.data.cache.daos.OrganizationsDao
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedorganization.CachedOrganization
import javax.inject.Inject


class RoomCache @Inject constructor(
    private val organizationsDao: OrganizationsDao
) : Cache {

    override suspend fun storeOrganizations(organizations: List<CachedOrganization>) {
        organizationsDao.insert(organizations)
    }
}