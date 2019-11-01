@file:JvmName("KeyboardUtils")

package com.example.flickrfindr.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

fun hideKeyboard(activity: FragmentActivity?) {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    val view = activity?.currentFocus
    view?.let {
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
        it.clearFocus()
    }
}