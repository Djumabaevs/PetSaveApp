package com.bignerdranch.android.petsaveapp.core.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.logging.Logger
import com.bignerdranch.android.petsaveapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.setImage(url: String) {
  Glide.with(this.context)
      .load(url.ifEmpty { null })
      .error(R.drawable.dog_placeholder)
      .centerCrop()
      .transition(DrawableTransitionOptions.withCrossFade())
      .into(this)
}

inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
  Logger.e(throwable, message)
  throwable.printStackTrace()

  /**
   * A [CoroutineExceptionHandler] can be called from any thread. So, if [action] is supposed to
   * run in the main thread, you need to be careful and call this function on the a scope that
   * runs in the main thread, such as a [viewModelScope].
  */
  launch {
    action(throwable)
  }
}