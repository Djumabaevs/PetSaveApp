package com.bignerdranch.android.petsaveapp.animalsnearyou.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.logging.Logger
import com.bignerdranch.android.petsaveapp.R
import com.bignerdranch.android.petsaveapp.animalsnearyou.domain.usecases.GetAnimals
import com.bignerdranch.android.petsaveapp.animalsnearyou.domain.usecases.RequestNextPageOfAnimals
import com.bignerdranch.android.petsaveapp.common.domain.model.NetworkException
import com.bignerdranch.android.petsaveapp.common.domain.model.NetworkUnavailableException
import com.bignerdranch.android.petsaveapp.common.domain.model.NoMoreAnimalsException
import com.bignerdranch.android.petsaveapp.common.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.common.domain.model.pagination.Pagination
import com.bignerdranch.android.petsaveapp.common.presentation.AnimalsAdapter
import com.bignerdranch.android.petsaveapp.common.presentation.Event
import com.bignerdranch.android.petsaveapp.common.presentation.model.mappers.UiAnimalMapper
import com.bignerdranch.android.petsaveapp.common.utils.DispatchersProvider
import com.bignerdranch.android.petsaveapp.common.utils.createExceptionHandler
import com.bignerdranch.android.petsaveapp.databinding.FragmentAnimalsNearYouBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class AnimalsNearYouFragment : Fragment() {

    companion object {
        private const val ITEMS_PER_ROW = 2
    }

    private val viewModel: AnimalsNearYouFragmentViewModel by viewModels()
    private val binding get() = _binding!!

    private var _binding: FragmentAnimalsNearYouBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAnimalsNearYouBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        requestInitialAnimalsList()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun createAdapter(): AnimalsAdapter {
        return AnimalsAdapter()
    }

    private fun setupRecyclerView(animalsNearYouAdapter: AnimalsAdapter) {
        binding.animalsRecyclerView.apply {
            adapter = animalsNearYouAdapter
            layoutManager = GridLayoutManager(requireContext(), ITEMS_PER_ROW)
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteScrollListener(layoutManager as GridLayoutManager))
        }
    }

    private fun createInfiniteScrollListener(
        layoutManager: GridLayoutManager
    ): RecyclerView.OnScrollListener {
        return object : InfiniteScrollListener(
            layoutManager,
            AnimalsNearYouFragmentViewModel.UI_PAGE_SIZE
        ) {
            override fun loadMoreItems() { requestMoreAnimals() }
            override fun isLoading(): Boolean = viewModel.isLoadingMoreAnimals
            override fun isLastPage(): Boolean = viewModel.isLastPage
        }
    }

    private fun requestMoreAnimals() {
        viewModel.onEvent(AnimalsNearYouEvent.RequestMoreAnimals)
    }

    private fun observeViewStateUpdates(adapter: AnimalsAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(state: AnimalsNearYouViewState, adapter: AnimalsAdapter) {
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.animals)
        handleNoMoreAnimalsNearby(state.noMoreAnimalsNearby)
        handleFailures(state.failure)
    }

    private fun handleNoMoreAnimalsNearby(noMoreAnimalsNearby: Boolean) {
        // Show a warning message and a prompt for the user to try a different
        // distance or postcode
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        }
        else {
            unhandledFailure.message!! }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun requestInitialAnimalsList() {
        viewModel.onEvent(AnimalsNearYouEvent.RequestInitialAnimalsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}