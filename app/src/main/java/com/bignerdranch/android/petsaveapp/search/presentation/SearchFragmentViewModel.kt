package com.bignerdranch.android.petsaveapp.search.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bignerdranch.android.logging.Logger
import com.bignerdranch.android.petsaveapp.core.domain.model.NoMoreAnimalsException
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.core.domain.model.pagination.Pagination
import com.bignerdranch.android.petsaveapp.core.presentation.Event
import com.bignerdranch.android.petsaveapp.core.presentation.model.mappers.UiAnimalMapper
import com.bignerdranch.android.petsaveapp.core.utils.DispatchersProvider
import com.bignerdranch.android.petsaveapp.core.utils.createExceptionHandler
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchParameters
import com.bignerdranch.android.petsaveapp.search.domain.model.SearchResults
import com.bignerdranch.android.petsaveapp.search.domain.usecases.GetSearchFilters
import com.bignerdranch.android.petsaveapp.search.domain.usecases.SearchAnimals
import com.bignerdranch.android.petsaveapp.search.domain.usecases.SearchAnimalsRemotely
//import dagger.assisted.Assisted
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragmentViewModel @ViewModelInject constructor(
  @Assisted private val savedStateHandle: SavedStateHandle,
  private val getSearchFilters: GetSearchFilters,
  private val searchAnimals: SearchAnimals,
  private val searchAnimalsRemotely: SearchAnimalsRemotely,
  private val uiAnimalMapper: UiAnimalMapper,
  private val dispatchersProvider: DispatchersProvider,
  private val compositeDisposable: CompositeDisposable
): ViewModel() {

  val state: LiveData<SearchViewState>
    get() = _state


  private val _state: MutableLiveData<SearchViewState> = MutableLiveData()
  private val querySubject = BehaviorSubject.create<String>()
  private val ageSubject = BehaviorSubject.createDefault<String>("")
  private val typeSubject = BehaviorSubject.createDefault<String>("")

  private var runningJobs = mutableListOf<Job>()
  private var isLastPage = false
  private var currentPage = 0

  init {
    _state.value = SearchViewState()
  }

  fun handleEvents(event: SearchEvent) {
    when(event) {
      is SearchEvent.PrepareForSearch ->  prepareForSearch()
      else -> onSearchParametersUpdate(event)
//      is SearchEvent.QueryInput -> updateQuery(event.input)
//      is SearchEvent.AgeValueSelected -> updateAgeValue(event.age)
//      is SearchEvent.TypeValueSelected -> updateTypeValue(event.type)
    }
  }

  private fun onSearchParametersUpdate(event: SearchEvent) {
    runningJobs.map { it.cancel() }

    resetStateIfNoRemoteResults()

    when (event) {
      is SearchEvent.QueryInput -> updateQuery(event.input)
      is SearchEvent.AgeValueSelected -> updateAgeValue(event.age)
      is SearchEvent.TypeValueSelected -> updateTypeValue(event.type)
    }
  }

  private fun resetStateIfNoRemoteResults() {
    if (state.value!!.isInNoSearchResultsState()) {
      _state.value = state.value!!.updateToSearching()
    }
  }

  private fun prepareForSearch() {
    loadMenuValues()
    setupSearchSubscription()
  }

  private fun loadMenuValues() {
    val exceptionHandler = createExceptionHandler(message = "Failed to get menu values!")

    viewModelScope.launch(exceptionHandler) {
      val (ages, types) = withContext(dispatchersProvider.io()) { getSearchFilters() }
      updateStateWithMenuValues(ages, types)
    }
  }

  private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
    return viewModelScope.createExceptionHandler(message) {
      onFailure(it)
    }
  }

  private fun updateStateWithMenuValues(ages: List<String>, types: List<String>) {
//    _state.value = state.value!!.copy(
//        ageMenuValues = Event(ages),
//        typeMenuValues = Event(types)
//    )
    _state.value = state.value!!.updateToReadyForSearch(ages, types)
  }

  private fun setupSearchSubscription() {
    searchAnimals(querySubject, ageSubject, typeSubject)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext { runningJobs.map { it.cancel() } }
        .subscribe(
            { onSearchResults(it) },
            { onFailure(it) }
        )
        .addTo(compositeDisposable)
  }

  private fun updateQuery(input: String) {
    resetPagination()

    querySubject.onNext(input)

    if (input.isEmpty()) {
      setNoSearchQueryStateIf()
    } else {
      setSearchingState()
    }
  }


  private fun resetPagination() {
    currentPage = 0
    isLastPage = false
  }

  private fun setSearchingState() {
//    _state.value = state.value!!.copy(noResultsState = false)
    _state.value = state.value!!.updateToSearching()
  }

  private fun setNoSearchQueryStateIf() {
    _state.value = state.value!!.copy(
        noSearchQueryState = true,
        searchResults = emptyList(),
        noResultsState = false
    )
  }

  private fun updateAgeValue(age: String) {
    ageSubject.onNext(age)
  }

  private fun updateTypeValue(type: String) {
    typeSubject.onNext(type)
  }

  private fun onSearchResults(searchResults: SearchResults) {
    val (animals, searchParameters) = searchResults

    if (animals.isEmpty()) {
      onEmptyCacheResults(searchParameters)
    } else {
      onAnimalList(animals)
    }
  }

  private fun onAnimalList(animals: List<Animal>) {
    _state.value = state.value!!.copy(
        noSearchQueryState = false,
        searchResults = animals.map { uiAnimalMapper.mapToView(it) },
        searchingRemotely = false,
        noResultsState = false
    )
  }

  private fun onEmptyCacheResults(searchParameters: SearchParameters) {
    searchRemotely(searchParameters)
    _state.value = state.value!!.copy(
        searchingRemotely = true,
        searchResults = emptyList()
    )
  }

  private fun searchRemotely(searchParameters: SearchParameters) {
    val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")

    val job = viewModelScope.launch(exceptionHandler) {
      val pagination = withContext(dispatchersProvider.io()) {
        Logger.d("Searching remotely...")

        searchAnimalsRemotely(++currentPage, searchParameters)
      }

      onPaginationInfoObtained(pagination)
    }

    runningJobs.add(job)

    job.invokeOnCompletion {
      it?.printStackTrace()
      runningJobs.remove(job)
    }
  }

  private fun onPaginationInfoObtained(pagination: Pagination) {
    currentPage = pagination.currentPage
    isLastPage = !pagination.canLoadMore
  }

  private fun onFailure(throwable: Throwable) {
    _state.value = if (throwable is NoMoreAnimalsException) {
      state.value!!.copy(searchingRemotely = false, noResultsState = true)
    } else {
      state.value!!.copy(failure = Event(throwable))
    }

  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
