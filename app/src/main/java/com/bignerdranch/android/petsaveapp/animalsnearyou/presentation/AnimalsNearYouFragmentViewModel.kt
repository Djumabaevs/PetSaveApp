package com.bignerdranch.android.petsaveapp.animalsnearyou.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.logging.Logger
import com.bignerdranch.android.petsaveapp.core.domain.model.NoMoreAnimalsException
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.core.domain.model.pagination.Pagination
import com.bignerdranch.android.petsaveapp.animalsnearyou.domain.usecases.GetAnimals
import com.bignerdranch.android.petsaveapp.animalsnearyou.domain.usecases.RequestNextPageOfAnimals
import com.bignerdranch.android.petsaveapp.core.presentation.Event
import com.bignerdranch.android.petsaveapp.core.presentation.model.mappers.UiAnimalMapper
import com.bignerdranch.android.petsaveapp.core.utils.DispatchersProvider
import com.bignerdranch.android.petsaveapp.core.utils.createExceptionHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import androidx.hilt.lifecycle.ViewModelInject
import kotlinx.coroutines.launch


class AnimalsNearYouFragmentViewModel @ViewModelInject constructor(
    private val requestNextPageOfAnimals: RequestNextPageOfAnimals,
    private val getAnimals: GetAnimals,
    private val uiAnimalMapper: UiAnimalMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
    }

    val state: LiveData<AnimalsNearYouViewState> get() = _state
    var isLoadingMoreAnimals: Boolean = false
    var isLastPage = false
    var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    fun setIsLoggedIn(loggedIn: Boolean) { isLoggedIn.value = loggedIn }


    private val _state = MutableLiveData<AnimalsNearYouViewState>()
    private var currentPage = 0

    init {
        _state.value = AnimalsNearYouViewState()
    }

    fun handleEvent(event: AnimalsNearYouEvent) {
        when(event) {
            is AnimalsNearYouEvent.LoadAnimals -> loadNextAnimalPage()
        }
    }

    init {
        _state.value = AnimalsNearYouViewState()

        subscribeToAnimalUpdates()
    }

    private fun subscribeToAnimalUpdates() {
        getAnimals()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNewAnimalList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun loadNextAnimalPage() {
        isLoadingMoreAnimals = true
        val errorMessage = "Failed to fetch nearby animals"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
            onFailure(it)
        }

        viewModelScope.launch(exceptionHandler) {
            val pagination = kotlinx.coroutines.withContext(dispatchersProvider.io()) {
                Logger.d("Requesting more animals.")

                requestNextPageOfAnimals(++currentPage)
            }

            onPaginationInfoObtained(pagination)

            isLoadingMoreAnimals = false
        }
    }

    private fun onNewAnimalList(animals: List<Animal>) {
        Logger.d("Got more animals!")
        val animalsNearYou = animals.map { uiAnimalMapper.mapToView(it) }

        // This ensures that new items are added below the already existing ones, thus avoiding
        // repositioning of items that are already visible, as it can provide for a confusing UX. A
        // nice alternative to this would be to add an "updatedAt" field to the Room entities, so
        // that we could actually order them by something that we completely control.
        val currentList = state.value?.animals.orEmpty()
        val newAnimals = animalsNearYou.subtract(currentList)
        val updatedList = currentList + newAnimals

        _state.value = state.value!!.copy(
            loading = false,
            animals = updatedList
        )
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
        isLastPage = !pagination.canLoadMore
    }

    private fun onFailure(failure: Throwable) {
        val noMoreAnimalsNearby = failure is NoMoreAnimalsException
        _state.value = state.value?.copy(
            noMoreAnimalsNearby = noMoreAnimalsNearby,
            failure = Event(failure)
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
