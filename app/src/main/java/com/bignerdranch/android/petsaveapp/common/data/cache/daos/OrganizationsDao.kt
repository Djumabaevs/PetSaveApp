package com.bignerdranch.android.petsaveapp.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bignerdranch.android.petsaveapp.common.data.cache.model.cachedorganization.CachedOrganization


@Dao
interface OrganizationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(organizations: List<CachedOrganization>)
}