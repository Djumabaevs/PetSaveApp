package com.bignerdranch.android.petsaveapp.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


interface DispatchersProvider {
  fun io(): CoroutineDispatcher = Dispatchers.IO
}
