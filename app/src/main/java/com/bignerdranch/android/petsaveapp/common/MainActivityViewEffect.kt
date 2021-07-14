package com.bignerdranch.android.petsaveapp.common

import androidx.annotation.NavigationRes

sealed class MainActivityViewEffect {
    data class SetStartDestination(@NavigationRes val destination: Int) : MainActivityViewEffect()
}