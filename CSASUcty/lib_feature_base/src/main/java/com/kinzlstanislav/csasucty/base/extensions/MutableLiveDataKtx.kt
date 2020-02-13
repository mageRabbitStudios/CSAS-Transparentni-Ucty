package com.kinzlstanislav.csasucty.base.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<*>.set(state: Any) {
    value = state
}