package com.bignerdranch.android.petsaveapp.core.utils


interface DispatchersProvider {
  fun io(): CoroutineDispatcher = Dispatchers.IO
}
