package com.reddit.client.top

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.cachedIn
import androidx.paging.map
import com.reddit.R
import com.reddit.base.BaseViewModel
import com.reddit.base.SingleLiveEvent
import com.reddit.sdk.ImageUrl
import com.reddit.sdk.ModuleApi
import com.reddit.sdk.timeDelta
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit


class TopViewModel @ViewModelInject constructor(
    @ApplicationContext context: Context,
    moduleApi: ModuleApi
) : BaseViewModel() {

    private val resources = context.resources

    private val _actionImageSaved = SingleLiveEvent<Unit>()
    val actionImageSaved: LiveData<Unit> = _actionImageSaved

    private val _actionOpenImage = SingleLiveEvent<ImageUrl>()
    val actionOpenImage: LiveData<ImageUrl> = _actionOpenImage

    val pagingTopListLiveData = moduleApi.getTop()
        .map { dataSource ->
            dataSource.map { item ->
                val hoursDelta = TimeUnit.MILLISECONDS.toHours(item.timeDelta).toInt()
                item.toTopRedditUi(
                    description = resources.getQuantityString(R.plurals.descriptionPlaceholder, hoursDelta, item.author, hoursDelta),
                    commentsCount = resources.getQuantityString(R.plurals.commentsPlaceholder, item.commentsNumber, item.commentsNumber),
                    onSaveImage = { onSaveImageToGallery(item.thumbnail) },
                    onOpenImage = { _actionOpenImage.value = item.thumbnail }
                )
            }
        }
        .cachedIn(coroutineScope)
        .asLiveData(coroutineScope.coroutineContext)

    private fun onSaveImageToGallery(imageUrl: String) {
        _actionImageSaved.value = Unit
    }
}
