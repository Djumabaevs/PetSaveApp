package com.bignerdranch.android.petsaveapp.onboarding.presentation


sealed class OnboardingEvent {
    data class PostcodeChanged(val newPostcode: String) : OnboardingEvent()
    data class DistanceChanged(val newDistance: String): OnboardingEvent()
    object SubmitButtonClicked: OnboardingEvent()
}