package com.bignerdranch.android.petsaveapp.common.data

import com.bignerdranch.android.petsaveapp.common.data.api.PetFinderApi
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiAnimalMapper
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiPaginationMapper
import com.bignerdranch.android.petsaveapp.common.data.api.utils.FakeServer
import com.bignerdranch.android.petsaveapp.common.data.cache.Cache
import com.bignerdranch.android.petsaveapp.common.data.di.PreferencesModule
import com.bignerdranch.android.petsaveapp.common.data.preferences.Preferences
import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(PreferencesModule::class)
class PetFinderAnimalRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: AnimalRepository
    private lateinit var api: PetFinderApi

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var cache: Cache

    @Inject
    lateinit var retrofit: Retrofit.Builder

    @Inject
    lateinit var apiAnimalMapper: ApiAnimalMapper

    @Inject
    lateinit var apiPaginationMapper: ApiPaginationMapper

    @BindValue
    @JvmField
    val preferences :Preferences = FakePreferences()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAnimals() {
    }

    @Test
    fun requestMoreAnimals() {
    }

    @Test
    fun storeAnimals() {
    }
}