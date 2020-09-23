package com.reddit.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

/**
 * function that help to identify type from java
 */
fun <T> Fragment.observe(liveData: LiveData<T>, onChanged: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, { onChanged(it) })
}