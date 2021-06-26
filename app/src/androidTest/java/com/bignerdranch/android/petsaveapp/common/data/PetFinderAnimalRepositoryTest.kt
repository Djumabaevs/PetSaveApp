package com.bignerdranch.android.petsaveapp.common.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.bignerdranch.android.petsaveapp.common.data.api.PetFinderApi
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiAnimalMapper
import com.bignerdranch.android.petsaveapp.common.data.api.model.mappers.ApiPaginationMapper
import com.bignerdranch.android.petsaveapp.common.data.api.utils.FakeServer
import com.bignerdranch.android.petsaveapp.common.data.cache.Cache
import com.bignerdranch.android.petsaveapp.common.data.cache.PetSaveDatabase
import com.bignerdranch.android.petsaveapp.common.data.cache.RoomCache
import com.bignerdranch.android.petsaveapp.common.data.di.CacheModule
import com.bignerdranch.android.petsaveapp.common.data.di.PreferencesModule
import com.bignerdranch.android.petsaveapp.common.data.preferences.FakePreferences
import com.bignerdranch.android.petsaveapp.common.data.preferences.Preferences
import com.bignerdranch.android.petsaveapp.common.domain.repositories.AnimalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.threeten.bp.Instant
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(PreferencesModule::class, CacheModule::class)
class PetFinderAnimalRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: AnimalRepository
    private lateinit var api: PetFinderApi

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var cache: Cache

    @Inject
    lateinit var database: PetSaveDatabase

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
        fakeServer.start()

        preferences.deleteTokenInfo()
        preferences.putToken("validToken")
        preferences.putTokenExpirationTime(
            Instant.now().plusSeconds(3600).epochSecond
        )
        preferences.putTokenType("Bearer")

        hiltRule.inject()

        api = retrofit
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(PetFinderApi::class.java)

        cache = RoomCache(database.animalsDao(), database.organizationsDao())

        repository = PetFinderAnimalRepository(
            api,
            cache,
            apiAnimalMapper,
            apiPaginationMapper
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAnimals() {
        fakeServer.shutdown()
    }

    @Test
    fun requestMoreAnimals() = runBlocking {
        //Given
        val expectedAnimalId = 124L
        fakeServer.setHappyPathDispatcher()

        //When
        val paginatedAnimals = repository.requestMoreAnimals(1, 100)

        //Then
        val animal = paginatedAnimals.animals.first()
        assertEquals(animal.id, expectedAnimalId)
    }

    @Test
    fun storeAnimals() {

    }
}

@Module
@InstallIn(SingletonComponent::class)
object TestCacheModule {

    @Provides
    fun provideRoomDatabase(): PetSaveDatabase {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            PetSaveDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }
}