package com.bignerdranch.android.petsaveapp.animalsnearyou.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bignerdranch.android.petsaveapp.databinding.FragmentAnimalsNearYouBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.petsaveapp.core.domain.model.NoMoreAnimalsException
import com.bignerdranch.android.petsaveapp.core.presentation.AnimalClickListener
import com.bignerdranch.android.petsaveapp.core.presentation.AnimalsAdapter
import com.bignerdranch.android.petsaveapp.core.presentation.Event
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import okio.IOException
import retrofit2.HttpException

@AndroidEntryPoint
class AnimalsNearYouFragment : Fragment() {

  companion object {
    private const val ITEMS_PER_ROW = 2
  }

  private val viewModel: AnimalsNearYouFragmentViewModel by activityViewModels()
  private val binding get() = _binding!!

  private var _binding: FragmentAnimalsNearYouBinding? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    _binding = FragmentAnimalsNearYouBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

//    try {
//      throw NullPointerException()
//    } catch (exception: Exception) {
//      FirebaseCrashlytics.getInstance().recordException(exception)
//    }

    setupUI()

    viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer<Boolean> { loggedIn ->
      if (loggedIn) {
        requestAnimals()
      }
    })
  }

  private fun setupUI() {
    val adapter = createAdapter()
    setupRecyclerView(adapter)
    observeViewStateUpdates(adapter)
  }

  private fun createAdapter(): AnimalsAdapter {
    return AnimalsAdapter().apply {
      setOnAnimalClickListener(object: AnimalClickListener {
        override fun onClick(animalId: Long) {
          val action = AnimalsNearYouFragmentDirections.actionAnimalsNearYouToAnimalDetailsFragment(animalId)
          findNavController().navigate(action)
        }
      })
    }
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
    return object : InfiniteScrollListener(layoutManager, AnimalsNearYouFragmentViewModel.UI_PAGE_SIZE) {
      override fun loadMoreItems() { requestAnimals() }
      override fun isLoading(): Boolean = viewModel.isLoadingMoreAnimals
      override fun isLastPage(): Boolean = viewModel.isLastPage
    }
  }

  private fun observeViewStateUpdates(adapter: AnimalsAdapter) {
    viewModel.state.observe(viewLifecycleOwner) {
      updateScreenState(it, adapter)
    }
  }

  private fun requestAnimals() {
    viewModel.handleEvent(AnimalsNearYouEvent.LoadAnimals)
  }

  private fun updateScreenState(state: AnimalsNearYouViewState, adapter: AnimalsAdapter) {
    binding.progressBar.isVisible = state.loading
    adapter.submitList(state.animals)
    handleNoMoreAnimalsNearby(state.noMoreAnimalsNearby)
    handleFailures(state.failure)
  }

  private fun handleNoMoreAnimalsNearby(noMoreAnimalsNearby: Boolean) {
    // hide everything, show a warning message and a prompt for the user to try a different
    // distance or postcode
  }

  private fun handleFailures(failure: Event<Throwable>?) {
    val unhandledFailure = failure?.getContentIfNotHandled() ?: return

    handleThrowable(unhandledFailure)
  }

  private fun handleThrowable(exception: Throwable) {
    val fallbackMessage = "An error occurred. Please try again later."
    val snackbarMessage = when (exception) {
      is NoMoreAnimalsException -> exception.message ?: fallbackMessage
      is IOException, is HttpException -> fallbackMessage
      else -> ""
    }

    if (snackbarMessage.isNotEmpty()) {
      Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
