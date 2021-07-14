package com.bignerdranch.android.petsaveapp.search.domain.usecases


class SearchAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
) {

  operator fun invoke(
      querySubject: BehaviorSubject<String>,
      ageSubject: BehaviorSubject<String>,
      typeSubject: BehaviorSubject<String>
  ): Flowable<SearchResults> {
    val query = querySubject
        .debounce(500L, TimeUnit.MILLISECONDS)
        .filter { it.length >= 2 }
        .distinctUntilChanged()

    return Observable.combineLatest(query, ageSubject, typeSubject, combiningFunction)
        .toFlowable(BackpressureStrategy.BUFFER)
        .switchMap {
            animalRepository.searchCachedAnimalsBy(SearchParameters(it.first, it.second, it.third))
        }
  }

  private val combiningFunction: Function3<String, String, String, Triple<String, String, String>>
    get() = Function3 {query, age, type -> Triple(query, age, type) }
}
