package com.reddit.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 *
 * !! IMPORTANT !! The observer, that will be notified of changes would be the first that was
 * observing this data.
 *
 * !! IMPORTANT !! This data not working if it was used in [MediatorLiveData] during different
 * transformation's. Notification propagated to all observers of transformed data
 *
 */
class SingleLiveEvent<T>() : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    constructor(value: T) : this() {
        this.value = value
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer<T> { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call(fromMainThread: Boolean) {
        if (fromMainThread) {
            value = null
        } else {
            postValue(null)
        }
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}