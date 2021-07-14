package com.bignerdranch.android.petsaveapp.core.data.cache.daos

@Dao
interface OrganizationsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(organizations: List<CachedOrganization>)
}