package com.bignerdranch.android.petsaveapp.details.presentation

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.core.view.isVisible
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.bignerdranch.android.petsaveapp.R
import com.bignerdranch.android.petsaveapp.core.presentation.model.UIAnimalDetailed
import com.bignerdranch.android.petsaveapp.core.utils.setImage
import com.bignerdranch.android.petsaveapp.core.utils.toEmoji
import com.bignerdranch.android.petsaveapp.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimalDetailsFragment: Fragment() {

    companion object{
        const val ANIMAL_ID = "id"
    }

    private val FLING_SCALE = 1f

    private val binding get() = _binding!!
    private var _binding: FragmentDetailsBinding? = null

    private val viewModel: AnimalDetailsViewModel by viewModels()

    private var animalId: Long? = null

    private val springForce: SpringForce by lazy {
        SpringForce().apply {
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            stiffness = SpringForce.STIFFNESS_VERY_LOW
        }
    }

    private val callScaleXSpringAnimation: SpringAnimation by lazy {
        SpringAnimation(binding.call, DynamicAnimation.SCALE_X).apply {
            spring = springForce
        }
    }

    private val callScaleYSpringAnimation: SpringAnimation by lazy {
        SpringAnimation(binding.call, DynamicAnimation.SCALE_Y).apply {
            spring = springForce
        }
    }

    private val FLING_FRICTION = 2f

    private val callFlingXAnimation: FlingAnimation by lazy {
        FlingAnimation(binding.call, DynamicAnimation.X).apply {
            friction = FLING_FRICTION
            setMinValue(0f)
            setMaxValue(binding.root.width.toFloat() - binding.call.width.toFloat())
        }
    }

    private val callFlingYAnimation: FlingAnimation by lazy {
        FlingAnimation(binding.call, DynamicAnimation.Y).apply {
            friction = FLING_FRICTION
            setMinValue(0f)
            setMaxValue(binding.root.height.toFloat() - binding.call.width.toFloat())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animalId = requireArguments().getLong(ANIMAL_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerState()
        val event = AnimalDetailsEvent.LoadAnimalDetails(animalId!!)
        viewModel.handleEvent(event)
    }

    private fun observerState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AnimalDetailsViewState.Loading -> {
                    displayLoading()
                }
                is AnimalDetailsViewState.Failure -> {
                    displayError()
                }
                is AnimalDetailsViewState.AnimalDetails -> {
                    displayPetDetails(state.animal, state.adopted)
                }
            }
        }
    }

    private fun displayPetDetails(animalDetails: UIAnimalDetailed, adopted: Boolean) {
        binding.call.scaleX = 0.6f
        binding.call.scaleY = 0.6f
        binding.call.isVisible = true
        binding.scrollView.isVisible = true
        stopAnimation()
        binding.name.text = animalDetails.name
        binding.description.text = animalDetails.description
        binding.image.setImage(animalDetails.photo)
        binding.sprayedNeutered.text = animalDetails.sprayNeutered.toEmoji()
        binding.specialNeeds.text = animalDetails.specialNeeds.toEmoji()
        binding.declawed.text = animalDetails.declawed.toEmoji()
        binding.shotsCurrent.text = animalDetails.shotsCurrent.toEmoji()
    }

    private fun displayError() {
        startAnimation(R.raw.lazy_cat)
        binding.scrollView.isVisible = false
        Snackbar.make(requireView(), R.string.an_error_occurred, Snackbar.LENGTH_SHORT).show()
    }

    private fun displayLoading() {
        startAnimation(R.raw.happy_dog)
        binding.scrollView.isVisible = false
    }

    private fun startAnimation(@RawRes animationRes: Int) {
        binding.loader.apply {
            isVisible = true
            setMinFrame(50)
            setMaxFrame(112)
            speed = 1.2f
            setAnimation(animationRes)
            playAnimation()
        }
        binding.loader.addValueCallback(
            KeyPath("icon_circle", "**"),
            LottieProperty.COLOR_FILTER,
            {
                PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP)
            }
        )
    }

    private fun stopAnimation() {
        binding.loader.apply {
            cancelAnimation()
            isVisible = false
        }
    }

    private fun areViewsOverlapping(view1: View, view2: View): Boolean {
        val firstRect = Rect()
        view1.getHitRect(firstRect)

        val secondRect = Rect()
        view2.getHitRect(secondRect)

        return Rect.intersects(firstRect, secondRect)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}