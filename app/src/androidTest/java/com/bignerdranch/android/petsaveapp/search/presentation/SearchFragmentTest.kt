package com.bignerdranch.android.petsaveapp.search.presentation

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bignerdranch.android.petsaveapp.R
import com.bignerdranch.android.petsaveapp.RxImmediateSchedulerRule
import com.bignerdranch.android.petsaveapp.TestCoroutineRule
import com.bignerdranch.android.petsaveapp.common.data.FakeRepository
import com.bignerdranch.android.petsaveapp.core.data.di.CacheModule
import com.bignerdranch.android.petsaveapp.core.data.di.PreferencesModule
import com.bignerdranch.android.petsaveapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(PreferencesModule::class, CacheModule::class, ActivityRetainedModule::class)
class SearchFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun searchFragment_testSearch_success() {
        // Given
        val nameToSearch = FakeRepository().remotelySearchableAnimal.name
        launchFragmentInHiltContainer<SearchFragment>()

        // When
        with (Espresso.onView(withId(R.id.search))) {
            perform(ViewActions.click())
            perform(typeSearchViewText(nameToSearch))
        }

        // Then
        with (Espresso.onView(withId(R.id.searchRecyclerView))) {
            check(ViewAssertions.matches(childCountIs(1)))
            check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText(nameToSearch))))
        }
    }

    private fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Type in SearchView"
            }

            override fun getConstraints(): Matcher<View> {
                return Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    private fun childCountIs(expectedChildCount: Int): Matcher<View> {
        return object: BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("RecyclerView with item count: $expectedChildCount")
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                return item?.adapter?.itemCount == expectedChildCount
            }
        }
    }
}