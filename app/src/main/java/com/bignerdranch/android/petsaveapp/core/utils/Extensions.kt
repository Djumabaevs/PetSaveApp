package com.bignerdranch.android.petsaveapp.core.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
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

fun ImageView.setImageWithCrossFade(url: String) {
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

/**
 * Extension that returns Yes if a Boolean is true, else No
 */
fun Boolean.toEnglish() = if (this) "Yes" else "No"

const val CHECK_EMOJI = 0x2714
const val CROSS_EMOJI = 0x274C
const val QUESTION_EMOJI = 0x2753

/**
 * Equivalent to toEnglish() but returns emoji unicode instead
 */
fun Boolean?.toEmoji() = if (this != null) {
    String(Character.toChars(if (this) CHECK_EMOJI else CROSS_EMOJI))
} else {
    String(Character.toChars(QUESTION_EMOJI))
}

fun Context.dpToPx(dp: Float) = this.getResources().getDisplayMetrics().density * dp

fun Paint.getTextWidth(string: String): Float {
    val rect = Rect()
    this.getTextBounds(string, 0, string.length, rect)
    return rect.width().toFloat()
}

