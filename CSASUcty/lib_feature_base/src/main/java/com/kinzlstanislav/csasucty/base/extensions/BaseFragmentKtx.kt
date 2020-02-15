package com.kinzlstanislav.csasucty.base.extensions

import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.IdRes
import com.kinzlstanislav.csasucty.base.view.BaseFragment

fun BaseFragment.disableTouchGestures() {
    requireActivity().window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun BaseFragment.enableTouchGestures() {
    requireActivity().window.clearFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun BaseFragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, length).show()
}

inline fun <reified T : Any> BaseFragment.bindArgument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as? T ?: throw IllegalArgumentException("Argument not passed for key: $key")
}

fun <ViewT : View> BaseFragment.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazy(LazyThreadSafetyMode.NONE) {
        requireActivity().findViewById<ViewT>(idRes)
    }
}