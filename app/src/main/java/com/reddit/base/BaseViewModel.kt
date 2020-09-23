package com.reddit.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reddit.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {
    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

        if (BuildConfig.DEBUG) {
            throw throwable
        } else {
            throwable.printStackTrace()
        }
    }

    protected val coroutineScope: CoroutineScope = viewModelScope + exceptionHandler
}